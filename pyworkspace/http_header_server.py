"""
HTTP 请求头打印服务器
学号: 3123004529
姓名: 纪泓鑫
班级: 软件工程3班

功能说明：
- 监听浏览器连接，在控制台打印 HTTP 请求头（与课件 ServerSocket 示例一致）
- 启动时自动选择空闲端口，并写入 http_server.port
- 同时写入 http_request.log

复现指南：见同目录 作业7实现计划.md
"""

import os
import socket
import signal
import sys
from datetime import datetime
from pathlib import Path

HOST = "127.0.0.1"
PORT_START = 18080
PORT_TRY_COUNT = 200
ENCODING = "iso-8859-1"
MAX_HEADER_SIZE = 64 * 1024

LOG_FILE = Path(__file__).with_name("http_request.log")
PORT_FILE = Path(__file__).with_name("http_server.port")

# 运行时确定
PORT = None


def log(msg):
    """输出到控制台并追加到日志文件。"""
    print(msg, flush=True)
    with open(LOG_FILE, "a", encoding="utf-8") as f:
        f.write(msg + "\n")


def find_free_port(host):
    """
    动态查找空闲端口：
    1. 优先尝试 18080 ~ 18080+199
    2. 若均被占用，则由系统分配任意空闲端口（bind 端口 0）
    """
    for port in range(PORT_START, PORT_START + PORT_TRY_COUNT):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            try:
                s.bind((host, port))
                return port
            except OSError:
                continue

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        s.bind((host, 0))
        return s.getsockname()[1]


def build_html_response(port):
    body = (
        "<!DOCTYPE html><html><head><meta charset='utf-8'>"
        "<title>HTTP Header Server</title></head><body>"
        "<h1>请求已收到</h1>"
        "<p>HTTP 请求头已打印到运行本程序的控制台，并写入 <code>http_request.log</code>。</p>"
        f"<p>访问地址: <a href='http://127.0.0.1:{port}/'>http://127.0.0.1:{port}/</a></p>"
        f"<p>本服务进程 PID: {os.getpid()}，端口: {port}</p>"
        "</body></html>"
    ).encode("utf-8")
    return (
        b"HTTP/1.1 200 OK\r\n"
        b"Content-Type: text/html; charset=utf-8\r\n"
        b"Connection: close\r\n"
        + f"Content-Length: {len(body)}\r\n\r\n".encode("ascii")
        + body
    )


def read_request_headers(client_socket):
    """读取 HTTP 请求头（直到空行 \\r\\n\\r\\n）。"""
    data = b""
    while b"\r\n\r\n" not in data and len(data) < MAX_HEADER_SIZE:
        chunk = client_socket.recv(4096)
        if not chunk:
            break
        data += chunk

    header_bytes, _, _ = data.partition(b"\r\n\r\n")
    return header_bytes.decode(ENCODING, errors="replace")


def print_request_headers(header_text, client_address):
    """打印 HTTP 请求头（课件逻辑：逐行输出，空行结束）。"""
    log(f"\n{'=' * 60}")
    log(f"[{datetime.now().strftime('%H:%M:%S')}] 连接来自: {client_address}")
    log(f"{'=' * 60}")

    if not header_text.strip():
        log("[WARN] 未收到请求头，请确认浏览器访问的是本程序启动时显示的地址")
        return

    for line in header_text.split("\r\n"):
        log(line)
        if line == "":
            break

    log(f"{'=' * 60}")
    log("[INFO] 请求头结束（空行）")


def handle_client(client_socket, client_address, port):
    """处理一次浏览器连接。"""
    client_socket.settimeout(30.0)
    try:
        header_text = read_request_headers(client_socket)
        print_request_headers(header_text, client_address)
        client_socket.sendall(build_html_response(port))
    except socket.timeout:
        log(f"[WARNING] 读取超时: {client_address}")
    except ConnectionResetError:
        log(f"[WARNING] 客户端断开: {client_address}")
    except Exception as e:
        log(f"[ERROR] {type(e).__name__}: {e}")
    finally:
        client_socket.close()
        log("[INFO] 连接已关闭\n")


def signal_handler(sig, frame):
    log("\n[INFO] 服务器正在关闭...")
    sys.exit(0)


def main():
    global PORT

    signal.signal(signal.SIGINT, signal_handler)

    LOG_FILE.write_text("", encoding="utf-8")

    PORT = find_free_port(HOST)

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    try:
        server_socket.bind((HOST, PORT))
        server_socket.listen(5)

        url = f"http://127.0.0.1:{PORT}/"
        PORT_FILE.write_text(str(PORT), encoding="utf-8")

        log(f"[INFO] HTTP 请求头打印服务器已启动")
        log(f"[INFO] 已自动选择空闲端口: {PORT}")
        log(f"[INFO] 本进程 PID: {os.getpid()}")
        log(f"[INFO] >>> 请在浏览器打开: {url}")
        log(f"[INFO] 端口已写入: {PORT_FILE}")
        log(f"[INFO] 请求头将显示在本窗口，并保存到: {LOG_FILE}")
        log(f"[INFO] 按 Ctrl+C 停止\n")

        while True:
            client_socket, client_address = server_socket.accept()
            log("[INFO] >>> 收到浏览器连接，正在读取 HTTP 请求...")
            handle_client(client_socket, client_address, PORT)

    except SystemExit:
        pass
    except OSError as e:
        log(f"[ERROR] 启动失败: {e}")
    finally:
        server_socket.close()


if __name__ == "__main__":
    main()
