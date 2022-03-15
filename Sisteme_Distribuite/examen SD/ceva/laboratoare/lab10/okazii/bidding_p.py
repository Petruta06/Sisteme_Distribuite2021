from RabbitMqConnection import *
import time
#TODO de configurat cozile a.i sa se realizeze conexiunea
# TODO de verificat ca merge
# TODO de vazut ca partea in care se opreste
class Bidding:
    def __init__(self, exchange, routing_key, queue):
        self.produce = RabbitMqProducer(exchange, routing_key)
        self.consume = RabbitMqConsumer(queue)
        #self.logger = RabbitMqProducer() # pentru consumer
        self.max = 0
        self.id = -1
        self.bidder =[]

    def receive(self):
        s = "Astept sa hotarasc cine castiga"
        print(s)
        #self.logger.send_message(s)
        try:
            mess = self.consume.receive()
            if(mess=="incheiat"):
                self.finish()
                return
            (id, bid)= mess.split("-")
            id = int(id)
            bid = int(bid)
            if(bid>self.max):
                self.max = bid
                self.id = id
        except Exception as e:
            print(e)

    def finish(self):
        s = "Castigatorul este -" + str(self.id)
        print(s)
       # self.logger.send_message(s)
        self.produce.send_message(s)

def main():
    b = Bidding("lab10.exchange",
                "bidding.routingkey",
                "bidding.queue")
    print("am creat unn bidding si astept clientii")
    print("Astept 10 secunde sa se conecteze bidder")
    time.sleep(5)
    b.receive()


if __name__ == '__main__':
    main()