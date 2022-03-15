from random import random, randint
from uuid import uuid4
from RabbitMqConnection import *



class Bidder:
    def __init__(self, routing_key, queue, exchange):
        self.my_id = uuid4()
        self.my_bid = randint(1000, 10_000)

        self.produce = RabbitMqProducer(exchange, routing_key)
        self.consumer = RabbitMqConsumer(queue)


    def bid(self):
        print("Sunt " + str(self.my_id )+ " si am pariat " + str(self.my_bid))
        message = str(self.my_id )+ "-" + str(self.my_bid)
        self.produce.send_message(message)


    def win(self):
        mess = None
        try:
            mess = self.consumer.receive()
            mess = mess.split("-")
            if int(mess[0])==self.my_id:
                print("Am castigat!")
            else:
                print("Am pierdut")
        except Exception as e:
            print(e)



    def run(self):
        self.bid()
        self.win()



def main():
    bidder = Bidder("bid.routingkey",
                    "bid.queue",
                    "lab10.exchange"
                    )
    bidder.run()
    print("am creat un bidder!")



if __name__ == '__main__':
    main()