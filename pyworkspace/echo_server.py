"""
应答服务器程序
学号: 3123004163
姓名: 张逸壕 
班级: 软件工程1班

功能说明：
- 使用socket实现一个简单的应答服务器
- 用户通过telnet连接服务器
- 服务器显示个人信息并回应用户输入
- 输入q或Q退出连接
"""

import socket
import sys
import signal


def handle_client(client_socket, client_address):
    """
    处理单个客户端连接
    :param client_socket: 客户端socket对象
    :param client_address: 客户端地址
    """
    print(f"[INFO] 客户端已连接: {client_address}")
    
    buffer = ""  # 用于累积接收到的字符
    
    try:
        # 发送欢迎信息，包含学号
        welcome_msg = "Hi, I am 3123004163.\r\nEnter 'q' or 'Q' to exit.\r\n"
        client_socket.send(welcome_msg.encode('utf-8'))
        
        # 持续接收并处理客户端消息
        while True:
            # 接收客户端数据（一次接收一个字符）
            data = client_socket.recv(1)
            
            # 如果没有接收到数据，说明客户端已断开连接
            if not data:
                print(f"[INFO] 客户端断开连接: {client_address}")
                break
            
            # 解码接收到的字符
            char = data.decode('utf-8')
            
            # 忽略telnet协议的特殊字符（IAC, DO, DON'T, WILL, WONT等）
            if ord(char) == 255:  # IAC (Interpret As Command)
                # 读取后续的命令字节
                client_socket.recv(2)  # 忽略telnet命令
                continue
            
            # 如果是回车符，表示一行输入完成
            if char == '\r' or char == '\n':
                # 跳过连续的\r\n
                if char == '\r':
                    # 尝试读取接下来的\n（如果有）
                    try:
                        next_char = client_socket.recv(1)
                        if next_char and next_char.decode('utf-8') != '\n':
                            # 如果不是\n，放回buffer
                            buffer += next_char.decode('utf-8')
                    except:
                        pass
                
                # 处理完整的行
                input_line = buffer.strip()
                buffer = ""  # 清空buffer
                
                if not input_line:  # 空行，跳过
                    continue
                    
                print(f"[DEBUG] 收到客户端消息: {input_line}")
                
                # 判断是否为退出命令
                if input_line.lower() == 'q':
                    # 发送退出消息
                    client_socket.send(b"bye!\r\n")
                    print(f"[INFO] 客户端主动退出: {client_address}")
                    break
                else:
                    # 返回用户输入的内容
                    response = f"Your input is: {input_line}\r\n"
                    client_socket.send(response.encode('utf-8'))
                    print(f"[DEBUG] 已回复客户端: {response.strip()}")
            else:
                # 累积字符到buffer
                buffer += char
    
    except ConnectionResetError:
        # 处理客户端异常断开的情况
        print(f"[WARNING] 客户端异常断开: {client_address}")
    except Exception as e:
        # 处理其他异常
        print(f"[ERROR] 处理客户端时发生错误: {e}")
    finally:
        # 确保关闭客户端连接
        client_socket.close()
        print(f"[INFO] 已关闭客户端连接: {client_address}")


def signal_handler(sig, frame):
    """处理Ctrl+C信号"""
    print("\n[INFO] 收到中断信号，服务器正在关闭...")
    sys.exit(0)


def main():
    """
    主函数：创建并运行服务器
    """
    # 注册信号处理器，使Ctrl+C能够正常退出
    signal.signal(signal.SIGINT, signal_handler)
    
    # 服务器配置
    HOST = 'localhost'  # 服务器地址
    PORT = 12345        # 服务器端口
    
    server_socket = None
    
    try:
        # 1. 创建TCP/IP socket
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        
        # 设置socket选项，允许地址重用
        server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        
        # 2. 绑定地址和端口
        server_address = (HOST, PORT)
        server_socket.bind(server_address)
        print(f"[INFO] 服务器绑定地址: {HOST}:{PORT}")
        
        # 3. 开始监听连接（参数1表示最多允许1个连接在队列中等待）
        server_socket.listen(1)
        print(f"[INFO] 服务器正在监听端口 {PORT}...")
        print(f"[INFO] 请使用 telnet {HOST} {PORT} 连接服务器")
        print(f"[INFO] 按 Ctrl+C 停止服务器\n")
        
        # 4. 循环接受客户端连接
        while True:
            # accept() 会阻塞直到有客户端连接
            # 设置超时，以便能够响应信号
            server_socket.settimeout(1.0)
            try:
                client_socket, client_address = server_socket.accept()
                # 处理客户端请求（同步处理，一次处理一个客户端）
                handle_client(client_socket, client_address)
            except socket.timeout:
                # 超时是正常的，继续循环
                continue
            
    except SystemExit:
        # 处理sys.exit()
        print("[INFO] 服务器已退出")
    except OSError as e:
        # 处理地址被占用等错误
        print(f"[ERROR] 服务器启动失败: {e}")
        print(f"[HINT] 请确保端口 {PORT} 未被其他程序占用")
    except Exception as e:
        # 处理其他异常
        print(f"[ERROR] 服务器发生错误: {e}")
    finally:
        # 确保关闭服务器socket
        if server_socket:
            server_socket.close()
            print("[INFO] 服务器已关闭")


if __name__ == '__main__':
    main()
