from RabbitMqConnection import *


class MessageProcces:
    def __init__(self, exchange, routing_key, queue):
        self.produce = RabbitMqProducer(exchange, routing_key)
        self.consume = RabbitMqConsumer(queue)
        self.logger = RabbitMqProducer("lab10.exchange", "log.routingkey")
        self.bidders = []

    def receive(self):
        s = "[Message Proccess] : Astept mesaje de la Auctioneer"
        print(s)
        self.logger.send_message(s)
        try:
            mess = self.consume.receive()
            if(mess == "incheiat"):
                self.finish()
            else:
                self.bidders.add(mess)

        except Exception as e:
            mess = "exceptie MessageProcess " + e.__str__()
            self.logger.send_message(mess)

    def finish(self):
        s = "[MP] Trimit datele catre Bidding"
        print(s)
        self.logger.send_message(s)
        for c in self.bidders:
            self.produce.send_message(c)
        self.produce.send_message("gata")


def main():
    m_p =  m_p = MessageProcces("lab10.exchange",
                         "mess_p.routingkey",
                         "mess_p.queue")
    print("Astept mesaje!")
    m_p.receive()


if __name__ == '__main__':
    main()

