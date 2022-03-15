import threading
import pika

config = {
    "host" : "localhost",
    "port" : 5672,
    "username" : "user",
    "password" : "user",
    "exchange" : "prodcons.direct",
    "routing_key" : "prodcons.routingkey",
    "queue" : "prodcons.queue"
}

credentials = pika.PlainCredentials(config["username"], config["password"])
parameters = (pika.ConnectionParameters(host=config["host"],
                                        port=config["port"],
                                        credentials=credentials))

class Consumer(threading.Thread):
    def __init__(self, topic):
        super().__init__()
        self.topic = topic

    def onReceiveMessage(self, blocking_channel, deliver, properties, message):
        result = message.decode("utf-8")
        blocking_channel.confirm_delivery()
        print(f"[Consumer] Received message : ${result}!\n")
        #blocking_channel.stop_consuming()

    def run(self) -> None:
        with pika.BlockingConnection(parameters) as connection:
            with connection.channel() as channel:
                channel.basic_consume(config["queue"], self.onReceiveMessage)
                try:
                    channel.start_consuming()
                # Don't recover connections closed by server
                except Exception:
                    channel.stop_consuming()


class Producer(threading.Thread):
    def __init__(self, topic):
        super().__init__()
        self.topic = topic

    def run(self) -> None:
        for i in range(1,100):
            message = f"Mesaj {i}"
            with pika.BlockingConnection(parameters) as connection:
                with connection.channel() as channel:
                    channel.queue_purge(config["queue"])
                    channel.basic_publish(exchange=config["exchange"],
                                          routing_key=config["routing_key"],
                                          body=message)
                    print(f"[Producer] Message sent : ${message}!")



if __name__ == "__main__":
    producer_Thread = Producer("topic_exemplu_python")
    consumer_Thread = Consumer("topic_exemplu_python")

    producer_Thread.start()
    consumer_Thread.start()

    producer_Thread.join()
    consumer_Thread.join()

