from RabbitMqConnection import *
import time

class Auctioneer:

    def __init__(self, routing_key, exchange, queue):
        self.auctioneer_producer = RabbitMqProducer( exchange,routing_key)
        self.auctioneer_consumer = RabbitMqConsumer(queue)



    def receive_bidder(self):
        print("Astept bidder")
        #self.logger.send_message("Astept bidder")
        while(True):
            try:
                mess = self.auctioneer_consumer.receive()
                (id, bid) = mess.split("-")
                s = str(id) + " a licitat " + str(bid)
                print(s)

                self.auctioneer_producer.send_message(mess)
            except Exception as e:
                s = "Exceptie in Auctioneer"
                print(s)
            break
        self.finish_auction()

    def finish_auction(self):
        s = "Licitatia s-a incheiat!"

        self.auctioneer_producer.send_message("incheiat")


def main():
    auctioneer = Auctioneer("auctioneer.routingkey",
                            "lab10.exchange",
                            "auctioneer.queue")

    time.sleep(10)
    auctioneer.receive_bidder()


if __name__ == '__main__':
    main()




