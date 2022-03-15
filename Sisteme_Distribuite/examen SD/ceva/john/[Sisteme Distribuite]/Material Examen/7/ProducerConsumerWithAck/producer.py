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


class Producer:
    def __init__(self, topic):
        self.topic = topic

    def onReceiveMessage(self, blocking_channel, deliver, properties, message):
        result = message.decode("utf-8")
        blocking_channel.confirm_delivery()
        print(f"[Producer] Received ack for message : ${result}!\n")
        blocking_channel.stop_consuming()

    def run(self) -> None:
        for i in range(1,3):
            message = f"Mesaj {i}"
            with pika.BlockingConnection(parameters) as connection:
                with connection.channel() as channel:
                    channel.queue_purge(config["senderqueue"])
                    channel.basic_publish(exchange=config["exchange"],
                                          routing_key=config["senderrouting_key"],
                                          body=message)
                    print(f"[Producer] Message sent : ${message}!")
                    print(f"### Waiting for ack from receiver!")
                    channel.basic_consume(config["receiverqueue"], self.onReceiveMessage)
                    try:
                        channel.start_consuming()
                    # Don't recover connections closed by server
                    except Exception:
                        channel.stop_consuming()

if __name__ == "__main__":
    producer_Thread = Producer("topic_exemplu_python")

    producer_Thread.run()
