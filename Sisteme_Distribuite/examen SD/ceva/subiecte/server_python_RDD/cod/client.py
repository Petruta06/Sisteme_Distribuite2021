import socket

SERVER_PORT  = 1234
HOST = "localhost"

class Client:

    def receive(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((HOST, SERVER_PORT))
            print("M-am conectat la server")
            data = s.recv(1024)
            print(data)

    def handle_mes(self, mess):
        print(mess)

def main():
    client = Client()
    client.receive()


if __name__ == '__main__':
    main()