import threading
import pika

config = {
    "host" : "localhost",
    "port" : 5672,
    "username" : "user",
    "password" : "user",
    "exchange" : "prodcons.direct",
    "senderqueue" : "prodcons.queue",
    "senderrouting_key": "prodcons.routingkey",
    "receiverqueue" : "prodcons.queue1",
    "receiverrouting_key" : "prodcons.routingkey1"
}

credentials = pika.PlainCredentials(config["username"], config["password"])
parameters = (pika.ConnectionParameters(host=config["host"],
                                        port=config["port"],
                                        credentials=credentials))

class Consumer:
    def __init__(self, topic):
        self.topic = topic

    def onReceiveMessage(self, blocking_channel, deliver, properties, message):
        result = message.decode("utf-8")
        blocking_channel.confirm_delivery()
        print(f"[Consumer] Received message : ${result}!\n")
        with pika.BlockingConnection(parameters) as connection:
            with connection.channel() as channel:
                channel.queue_purge(config["receiverqueue"])
                channel.basic_publish(exchange=config["exchange"],
                                      routing_key=config["receiverrouting_key"],
                                      body=result)
                print(f"[Consumer] Sending ack for message : ${result}!")

    def run(self) -> None:
        with pika.BlockingConnection(parameters) as connection:
            with connection.channel() as channel:
                print(f"### Waiting for message from sender!")
                channel.basic_consume(config["senderqueue"], self.onReceiveMessage)
                try:
                    channel.start_consuming()
                # Don't recover connections closed by server
                except Exception:
                    channel.stop_consuming()

if __name__ == "__main__":
    consumer_Thread = Consumer("topic_exemplu_python")

    consumer_Thread.run()

