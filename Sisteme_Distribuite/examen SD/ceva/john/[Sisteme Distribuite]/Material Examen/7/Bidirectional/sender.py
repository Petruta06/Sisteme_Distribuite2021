import pika

Parameters = {
    "host" : "localhost",
    "exchange" : "BidirectionalExchange"
}


with pika.BlockingConnection(pika.ConnectionParameters(Parameters["host"])) as connection:
    channel = connection.channel()
    channel.exchange_declare(exchange=Parameters["exchange"])
    message = "Hello world!"
    channel.basic_publish(exchange=Parameters["exchange"], routing_key='', body=message)
    print("Message sent!")