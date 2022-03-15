
import socket

HOST = 'localhost'  # The server's hostname or IP address
PORT = 1234        # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    while(1):
        data = s.recv(1024)
        print(data)
