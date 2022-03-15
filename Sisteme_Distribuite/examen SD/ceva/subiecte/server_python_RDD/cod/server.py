import requests
import socket
import time
from datetime import date
from datetime import timedelta

HOST = "localhost"
SERVER_PORT = 1234


class News:
    url = "https://finnhub.io/docs/api#"

    def __init__(self, API_key):
        self.__API_key = API_key
        self.__url__ ='https://finnhib.io/api/v1/stock/symbol?exchange =US'+self.__API_key

    def getRequest(self):
        r =requests.get(self.__url__)
        return r

class NewsGen:
    def __init__(self, API_key):
        self.__API_key__ = API_key

    def get_news(self, company_simbol):
        today = date.today()
        today_date = today.strftime("%Y-%m-%d")
        last_date = today - timedelta(days=14)
        link = 'https://finmhub/api/v1/company-news?symbol='+company_simbol+'&from='+last_date + '&to'+ today_date+'token=' +self.__API_key__

        r = requests.get(link)
        return r

class Sever:

    def __init__(self):
        self.__API_key = 'brmu4j7rh5r90ebn6irg'
        self.__name = 'APPL'
        self.news = News(self.__API_key)
        self.__gen = NewsGen(self.__API_key)


    def run(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.bind((HOST, SERVER_PORT))
            print("Astept sa se conecteze clientii")
            s.listen()
            conn, addr = s.accept() # aceasta este blocanta
            print('S-a conectat  un client')
            list_news = self.news.getRequest().json()
            for n in list_news:
                c = self.__gen.get_news(n['symbol']).text
                if len(c)>2:
                    print(c)
                    conn.sendall(bytes(c, 'utf-8'))
                    time.sleep(5)
                else:
                    print('Nu sunt stiri pentru compania '+ n['symbol'])


def main():
    server = Sever()
    server.run()

if __name__ == '__main__':
    main()
