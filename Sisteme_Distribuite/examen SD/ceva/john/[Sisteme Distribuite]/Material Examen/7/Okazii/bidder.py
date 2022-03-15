import pika
from random import randint

config = {
    "host" : "localhost",
    "port" : 5672,
    "username" : "user",
    "password" : "user",
    "exchange" : "okazii.BAe",
    "routing_key" : "okazii.Bbrk",
    #"queue" : "okazii.Bbq"
    "queue" : "okazii.Awq"
}

credentials = pika.PlainCredentials(username=config["username"],
                                    password=config["password"])
parameters = (pika.ConnectionParameters(config["host"]),
              pika.ConnectionParameters(config["port"]),
              credentials)


class Bidder:
    def __init__(self):
        self.my_bind = None
        self.my_id = randint(1000, 5000)
        self.bid()

    def bid(self):
        # se genereaza oferta ca numar aleator intre 1000 si 10000
        self.my_bid = randint(1000, 10000)

        # construim mesajul pentru licitatie
        print(f"Trimit licitatia mea : {self.my_bid}...")
        message = f"[{self.my_id}] licitez {self.my_bid}"
        with pika.BlockingConnection(parameters) as connection:
            with connection.channel() as channel:
                #channel.queue_purge(config["queue"]) : atentie! cealalta queue!
                channel.basic_publish(
                    exchange=config["exchange"],
                    routing_key=config["routing_key"],
                    body=message
                )

    def actionOnWinner(self, blocking_channel, deliver, properties, message):
        result = message.decode("utf-8")
        blocking_channel.confirm_delivery()
        msg, id = result.split()
        id = id[1:-1]
        if id == self.my_id:
            print("I wooooon!!!")
        else:
            print("I lost! :(")
        blocking_channel.stop_consuming()

    def getWinner(self):
        with pika.BlockingConnection(parameters) as connection:
            with connection.channel() as channel:
                channel.basic_consume(config["queue"], self.actionOnWinner)
                try:
                    channel.start_consuming()
                except Exception:
                    print("Error on attempting to consume!")

if __name__ == "__main__":
    candidate = Bidder()
    candidate.bid()
    candidate.getWinner()