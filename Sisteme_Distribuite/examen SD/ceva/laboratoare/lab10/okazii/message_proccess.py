from RabbitMqConnection import *
import time

class MessageProcces:
    def __init__(self, exchange, routing_key, queue):
        self.produce = RabbitMqProducer(exchange, routing_key)
        self.consume = RabbitMqConsumer(queue)

        self.bidders = []

    def receive(self):
        s = "[Message Proccess] : Astept mesaje de la Auctioneer"
        print(s)

        try:
            mess = self.consume.receive()
            if(mess == "incheiat"):
                self.finish()
            else:
                self.bidders.add(mess)

        except Exception as e:
           print(e)

    def finish(self):
        s = "[MP] Trimit datele catre Bidding"
        print(s)
        for c in self.bidders:
            self.produce.send_message(c)
        self.produce.send_message("gata")


def main():
    m_p = MessageProcces("lab10.exchange",
                         "mess_p.routingkey",
                         "mess_p.queue")
    print("Astept 5 secunde sa se conecteze bidder")
    time.sleep(5)
    m_p.receive()


if __name__ == '__main__':
    main()

