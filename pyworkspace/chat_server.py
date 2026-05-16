"""
多用户聊天服务器程序
学号: 3123004163
姓名: 张逸壕
班级: 软件工程1班

功能说明：
- 基于作业5应答服务器改造，支持多用户同时在线
- 实现用户注册、登录、查找、私聊、退出登录
- 使用线程池处理客户端连接，消息队列实现私聊转发
"""

import os
import queue
import signal
import socket
import sys
import threading
from concurrent.futures import ThreadPoolExecutor
from datetime import datetime
from typing import Optional

from user_store import FileUserStore

STUDENT_ID = "3123004163"
HOST = "localhost"
PORT = 12345
LOG_FILE = "chat_server.log"
USERS_FILE = os.path.join("data", "users.json")

HELP_TEXT = (
  f"Hi, I am {STUDENT_ID}.\r\n"
  "Commands:\r\n"
  "  001,id,password     register\r\n"
  "  002,id,password     login\r\n"
  "  003,id              logout\r\n"
  "  004,all             list online users\r\n"
  "  005,src,des,msg     private message\r\n"
)


def log_operation(message: str) -> None:
  timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
  line = f"[{timestamp}] {message}"
  print(line)
  try:
    with open(LOG_FILE, "a", encoding="utf-8") as file:
      file.write(line + "\n")
  except OSError as error:
    print(f"[ERROR] 写入日志失败: {error}")


def is_valid_id(user_id: str) -> bool:
  return bool(user_id) and " " not in user_id and "," not in user_id


def safe_send(sock: socket.socket, text: str) -> bool:
  try:
    sock.send(text.encode("utf-8"))
    return True
  except (OSError, ConnectionError):
    return False


def read_line(sock: socket.socket) -> Optional[str]:
  buffer = ""
  while True:
    data = sock.recv(1)
    if not data:
      return None

    char = data.decode("utf-8", errors="ignore")
    if ord(char) == 255:
      sock.recv(2)
      continue

    if char in ("\r", "\n"):
      if char == "\r":
        try:
          next_data = sock.recv(1)
          if next_data and next_data.decode("utf-8", errors="ignore") != "\n":
            buffer += next_data.decode("utf-8", errors="ignore")
        except OSError:
          pass
      line = buffer.strip()
      buffer = ""
      return line

    buffer += char


class UserRegistry:
  def __init__(self) -> None:
    self._users: dict[str, str] = {}
    self._lock = threading.Lock()

  def load_from_store(self, store: FileUserStore) -> None:
    self._users = store.load_all()
    log_operation(f"已加载注册用户 {len(self._users)} 个")

  def register(self, user_id: str, password: str) -> tuple[bool, str]:
    with self._lock:
      if user_id in self._users:
        return False, "id已存在"
      self._users[user_id] = password
    return True, "OK"

  def verify(self, user_id: str, password: str) -> tuple[bool, str]:
    with self._lock:
      if user_id not in self._users:
        return False, "用户不存在"
      if self._users[user_id] != password:
        return False, "密码错误"
      return True, "OK"


class OnlineUsers:
  def __init__(self) -> None:
    self._id_to_sock: dict[str, socket.socket] = {}
    self._sock_to_id: dict[socket.socket, str] = {}
    self._lock = threading.Lock()

  def bind(self, user_id: str, sock: socket.socket) -> None:
    with self._lock:
      self._id_to_sock[user_id] = sock
      self._sock_to_id[sock] = user_id

  def unbind(self, user_id: str) -> None:
    with self._lock:
      sock = self._id_to_sock.pop(user_id, None)
      if sock is not None:
        self._sock_to_id.pop(sock, None)

  def get_socket(self, user_id: str) -> Optional[socket.socket]:
    with self._lock:
      return self._id_to_sock.get(user_id)

  def get_id(self, sock: socket.socket) -> Optional[str]:
    with self._lock:
      return self._sock_to_id.get(sock)

  def is_online(self, user_id: str) -> bool:
    with self._lock:
      return user_id in self._id_to_sock

  def list_online(self) -> list[str]:
    with self._lock:
      return list(self._id_to_sock.keys())

  def send_to(self, user_id: str, text: str) -> bool:
    sock = self.get_socket(user_id)
    if sock is None:
      return False
    return safe_send(sock, text)

  def remove_by_socket(self, sock: socket.socket) -> Optional[str]:
    with self._lock:
      user_id = self._sock_to_id.pop(sock, None)
      if user_id is not None:
        self._id_to_sock.pop(user_id, None)
      return user_id


class SendMessageService(threading.Thread):
  def __init__(self, msg_queue: queue.Queue, online_users: OnlineUsers) -> None:
    super().__init__(daemon=True)
    self.msg_queue = msg_queue
    self.online_users = online_users

  def run(self) -> None:
    while True:
      src_id, des_id, message = self.msg_queue.get()
      try:
        self.forward_message(src_id, des_id, message)
      finally:
        self.msg_queue.task_done()

  def forward_message(self, src_id: str, des_id: str, message: str) -> None:
    line = f"106,{src_id},{des_id},{message}\r\n"
    if self.online_users.send_to(des_id, line):
      log_operation(f"转发私聊成功: {src_id} -> {des_id}: {message}")
      return

    log_operation(f"转发私聊失败，目标离线: {src_id} -> {des_id}")
    error_line = f"ERR,{des_id},FAIL:目标用户不在线\r\n"
    self.online_users.send_to(src_id, error_line)


def do_register(parts: list[str], registry: UserRegistry, store: FileUserStore) -> str:
  if len(parts) < 2:
    return "101,,FAIL:参数不足\r\n"
  user_id, password = parts[0], parts[1]
  if not is_valid_id(user_id):
    return f"101,{user_id},FAIL:id格式非法\r\n"

  ok, reason = registry.register(user_id, password)
  if ok:
    try:
      store.save_user(user_id, password)
      log_operation(f"用户注册成功: {user_id}")
      return f"101,{user_id},OK\r\n"
    except OSError as error:
      log_operation(f"用户注册持久化失败: {user_id}, {error}")
      return f"101,{user_id},FAIL:保存失败\r\n"

  log_operation(f"用户注册失败: {user_id}, {reason}")
  return f"101,{user_id},FAIL:{reason}\r\n"


def do_login(
  parts: list[str],
  sock: socket.socket,
  registry: UserRegistry,
  online: OnlineUsers,
) -> tuple[str, Optional[str]]:
  if len(parts) < 2:
    return "102,,FAIL:参数不足\r\n", None
  user_id, password = parts[0], parts[1]
  if not is_valid_id(user_id):
    return f"102,{user_id},FAIL:id格式非法\r\n", None

  ok, reason = registry.verify(user_id, password)
  if not ok:
    log_operation(f"用户登录失败: {user_id}, {reason}")
    return f"102,{user_id},FAIL:{reason}\r\n", None

  if online.is_online(user_id):
    log_operation(f"用户登录失败: {user_id}, 用户已在线")
    return f"102,{user_id},FAIL:用户已在线\r\n", None

  online.bind(user_id, sock)
  log_operation(f"用户登录成功: {user_id}")
  return f"102,{user_id},OK\r\n", user_id


def do_logout(parts: list[str], current_user: Optional[str], online: OnlineUsers) -> tuple[str, Optional[str]]:
  if len(parts) < 1:
    return "103,,FAIL:参数不足\r\n", current_user
  user_id = parts[0]
  if current_user is None:
    return f"103,{user_id},FAIL:未登录\r\n", None
  if user_id != current_user:
    return f"103,{user_id},FAIL:只能退出当前账号\r\n", current_user

  online.unbind(user_id)
  log_operation(f"用户退出登录: {user_id}")
  return f"103,{user_id}\r\n", None


def do_search(current_user: Optional[str], online: OnlineUsers) -> str:
  if current_user is None:
    return "104,,FAIL:未登录\r\n"
  online_ids = online.list_online()
  data = ",".join([current_user] + online_ids)
  log_operation(f"用户查找在线列表: {current_user} -> {online_ids}")
  return f"104,{data}\r\n"


def do_send_message(parts: list[str], current_user: Optional[str], msg_queue: queue.Queue) -> str:
  if current_user is None:
    return "ERR,,FAIL:未登录\r\n"
  if len(parts) < 3:
    return "ERR,,FAIL:参数不足\r\n"

  src_id, des_id, message = parts[0], parts[1], parts[2]
  if not is_valid_id(src_id) or not is_valid_id(des_id):
    return "ERR,,FAIL:id格式非法\r\n"
  if src_id != current_user:
    return f"ERR,{des_id},FAIL:srcId与当前用户不一致\r\n"
  if "," in message:
    return f"ERR,{des_id},FAIL:消息不能包含逗号\r\n"

  msg_queue.put((src_id, des_id, message))
  log_operation(f"私聊消息入队: {src_id} -> {des_id}: {message}")
  return f"ACK,{des_id},OK\r\n"


def handle_command(
  line: str,
  sock: socket.socket,
  current_user: Optional[str],
  registry: UserRegistry,
  online: OnlineUsers,
  store: FileUserStore,
  msg_queue: queue.Queue,
) -> tuple[str, Optional[str]]:
  parts = line.strip().split(",")
  if not parts or not parts[0]:
    return "000,unknown command\r\n", current_user

  cmd = parts[0]
  data = parts[1:]

  if cmd == "001":
    return do_register(data, registry, store), current_user
  if cmd == "002":
    return do_login(data, sock, registry, online)
  if cmd == "003":
    return do_logout(data, current_user, online)
  if cmd == "004":
    return do_search(current_user, online), current_user
  if cmd == "005":
    return do_send_message(data, current_user, msg_queue), current_user

  if current_user is None and cmd not in ("000",):
    return "ERR,,FAIL:请先登录\r\n", current_user

  return "000,unknown command\r\n", current_user


def handle_client_connection(
  client_socket: socket.socket,
  client_address,
  msg_queue: queue.Queue,
  registry: UserRegistry,
  online: OnlineUsers,
  store: FileUserStore,
) -> None:
  current_user: Optional[str] = None
  log_operation(f"客户端已连接: {client_address}")
  safe_send(client_socket, HELP_TEXT)

  try:
    while True:
      line = read_line(client_socket)
      if line is None:
        break
      if not line:
        continue

      print(f"[DEBUG] 收到客户端消息: {line}")
      if current_user is None and not line.startswith(("001", "002")):
        response = "ERR,,FAIL:请先注册或登录\r\n"
      else:
        response, current_user = handle_command(
          line,
          client_socket,
          current_user,
          registry,
          online,
          store,
          msg_queue,
        )

      if not safe_send(client_socket, response):
        break

  except ConnectionResetError:
    log_operation(f"客户端异常断开: {client_address}")
  except Exception as error:
    log_operation(f"处理客户端错误: {client_address}, {error}")
  finally:
    removed_id = online.remove_by_socket(client_socket)
    if removed_id:
      log_operation(f"连接关闭，清理在线用户: {removed_id}")
    client_socket.close()
    log_operation(f"已关闭客户端连接: {client_address}")


def signal_handler(sig, frame) -> None:
  print("\n[INFO] 收到中断信号，服务器正在关闭...")
  sys.exit(0)


def main() -> None:
  signal.signal(signal.SIGINT, signal_handler)

  store = FileUserStore(USERS_FILE)
  registry = UserRegistry()
  registry.load_from_store(store)

  msg_queue: queue.Queue = queue.Queue()
  online = OnlineUsers()

  sender = SendMessageService(msg_queue, online)
  sender.start()
  log_operation("SendMessageService 已启动")

  server_socket = None
  executor = None

  try:
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((HOST, PORT))
    server_socket.listen(30)
    print(f"聊天服务器已启动 {HOST}:{PORT}，学号 {STUDENT_ID}")
    log_operation(f"聊天服务器已启动 {HOST}:{PORT}，学号 {STUDENT_ID}")
    print(f"请使用 telnet {HOST} {PORT} 连接服务器")
    print("按 Ctrl+C 停止服务器\n")

    executor = ThreadPoolExecutor(max_workers=30)
    while True:
      server_socket.settimeout(1.0)
      try:
        client_socket, client_address = server_socket.accept()
      except socket.timeout:
        continue

      executor.submit(
        handle_client_connection,
        client_socket,
        client_address,
        msg_queue,
        registry,
        online,
        store,
      )

  except SystemExit:
    print("[INFO] 服务器已退出")
  except OSError as error:
    print(f"[ERROR] 服务器启动失败: {error}")
    print(f"[HINT] 请确保端口 {PORT} 未被其他程序占用")
  except Exception as error:
    print(f"[ERROR] 服务器发生错误: {error}")
  finally:
    if executor is not None:
      executor.shutdown(wait=False)
    if server_socket is not None:
      server_socket.close()
      print("[INFO] 服务器已关闭")


if __name__ == "__main__":
  main()
