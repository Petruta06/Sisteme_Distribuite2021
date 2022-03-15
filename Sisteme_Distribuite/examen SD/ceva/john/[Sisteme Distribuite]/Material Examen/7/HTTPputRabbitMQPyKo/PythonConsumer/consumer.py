import pika

config = {
    "host" : "localhost",
    "port" : 5672,
    "username" : "user",
    "password" : "user",
    "exchange" : "httpapp.direct",
    "routingkey" : "httpapp.routingkey",
    "queue" : "httpapp.queue"
}

credentials = pika.PlainCredentials(config["username"], config["password"])

parameters = (pika.ConnectionParameters(host=config["host"],
                                        port=config["port"],
                                        credentials=credentials))

def onReceiveMessage(blocking_channel, deliver, properties, message):
    result = message.decode("utf-8")
    blocking_channel.confirm_delivery()
    print(f"Received message : ${result}!")

if __name__ == "__main__":
    with pika.BlockingConnection(parameters) as connection:
        with connection.channel() as channel:
            print("#Waiting for requests !")
            channel.basic_consume(config["queue"], onReceiveMessage)
            channel.start_consuming()
