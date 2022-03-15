import pika

# send a message to the queue
Parameters = {
    "host" : "localhost",
    "queue" : "helloqueue",
    "exchange" : "",
    "routing_key" : "helloqueue"
}
# establish connection
message = "Hello from HelloQueue!"
with pika.BlockingConnection(pika.ConnectionParameters(Parameters["host"])) as connection:
    channel = connection.channel()
    # create a queue to which messages will be delivered
    channel.queue_declare(queue=Parameters["queue"])
    channel.basic_publish(exchange=Parameters["exchange"],
                          routing_key=Parameters["routing_key"],
                          body=message)
    print(f"[x] Send message \"{message}\"!")
