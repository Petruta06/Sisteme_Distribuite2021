import requests
import socket
import time
from datetime import date
from datetime import timedelta

HOST = "localhost"
SERVER_PORT = 1234

def read():
    f = open("news.txt")
    sir = f.read()
    return sir.split(" ")

class Sever:

    def __init__(self):
        self.words = read()
        #print(self.words)
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.bind((HOST, SERVER_PORT))


    def run(self):
            print("Astept sa se conecteze clientii")
            self.sock.listen(5)
            conn, addr = self.sock.accept() # aceasta este blocanta
            print('S-a conectat  un client')
            #list_news = self.news.getRequest().json()
            for c in self.words:
                conn.send(bytes(c + "\n", "utf-8"))
                time.sleep(1)
            conn.send("stop")

    def close(self):
        self.sock.close()


def main():
    server = Sever()
    server.run()


if __name__ == '__main__':
    main()
