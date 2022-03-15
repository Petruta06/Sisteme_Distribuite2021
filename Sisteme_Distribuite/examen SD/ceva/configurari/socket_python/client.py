import socket


def main():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(("localhost", 1234))
    while True:
        data = sock.recv(1024)
        print(data)
        if(data=="stop"):
            break

if __name__ == '__main__':
    main()