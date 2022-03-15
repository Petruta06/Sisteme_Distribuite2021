import pika

# send a message to the queue
Parameters = {
    "host" : "localhost",
    "queue" : "helloqueue",
    "exchange" : "",
    "routing_key" : "helloqueue"
}
def receivedMessage(ch, method, properties, body):
    print("Message received : %s" %(body))


with pika.BlockingConnection(pika.ConnectionParameters(Parameters["host"])) as connection:
    channel = connection.channel()
    # create a queue to which messages will be delivered
    channel.queue_declare(queue=Parameters["queue"])
    channel.basic_consume(queue=Parameters["queue"], on_message_callback=receivedMessage, auto_ack=True)
    channel.start_consuming()