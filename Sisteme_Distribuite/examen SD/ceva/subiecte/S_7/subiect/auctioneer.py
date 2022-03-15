from RabbitMqConnection import *


class Auctioneer:

    def __init__(self, routing_key, exchange, queue):
        self.auctioneer_producer = RabbitMqProducer( exchange, routing_key)
        self.auctioneer_consumer = RabbitMqConsumer(queue)
        self.logger = RabbitMqProducer("lab10.exchange", "log.routingkey")


    def receive_bidder(self):
        print("Astept bidder")
        self.logger.send_message("Astept bidder")
        while(True):
            try:
                mess = self.auctioneer_consumer.receive()
                (id, bid) = mess.split("-")
                s = str(id) + " a licitat " + str(bid)
                print(s)
                self.logger.send_message(s)
                self.auctioneer_producer.send_message(mess)
            except Exception as e:
                mess = "exceptie Auctioneer " + e.__str__()
                self.logger.send_message(mess)
            break
        self.finish_auction()

    def finish_auction(self):
        s = "Licitatia s-a incheiat!"
        self.logger.send_message(s)
        self.auctioneer_producer.send_message("incheiat")


def main():
    auctioneer = Auctioneer("auctioneer.routingkey",
                            "lab10.exchange",
                            "auctioneer.queue")
    auctioneer.receive_bidder()


if __name__ == '__main__':
    main()




