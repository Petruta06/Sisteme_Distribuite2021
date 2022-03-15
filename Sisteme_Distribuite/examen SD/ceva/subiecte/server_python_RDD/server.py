import socket

def main():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(("localhost", 1234))
    sock.listen(3)
    conn, addr =sock.accept()
    while(True):
        




main()
