import requests
import socket
import time

HOST ="localhost"
SERVER_PORT = 1234


class Price:
    def __init__(self, token, symbol):
        self.token = token
        self.symbol = symbol
        self.url = 'https://finnhub.io/api/v1/stock/price-target?symbol='\
                   +symbol+'&token='+token

    def get_price(self):
        print("Fac cerere pentru resurse")
        r = requests.get(self.url)
        print("Am obtinut  \n" + r.text)
        return r


class Server:
    def __init__(self, p):
        self.price = p
        self.list =[]
        print("Am creat socket-ul!")
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.bind((HOST, SERVER_PORT))



    def run(self):
        s = self.price.get_price()
        print("Astept sa se conecteze clientii")
        self.sock.listen(1)
        conn, addr = self.sock.accept()
        print("S-a conectat un client si incep sa trimit date")
        for c in s.json():
            conn.send(bytes(c+"\n" , "utf-8"))
            time.sleep(3) # astept



def main():
    p = Price('brmrda7rh5rcss140no0', 'NFLX')
    server = Server(p)
    server.run()

if __name__ == '__main__':
   main()

