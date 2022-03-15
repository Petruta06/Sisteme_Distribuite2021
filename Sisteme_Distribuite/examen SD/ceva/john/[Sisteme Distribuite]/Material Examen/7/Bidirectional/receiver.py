import pika

Parameters = {
    "host" : "localhost",
    "exchange" : "BidirectionalExchange",
    "queue" : "matilda"
}


with pika.BlockingConnection(pika.ConnectionParameters(Parameters["host"])) as connection:
    channel = connection.channel()
    channel.exchange_declare(exchange=Parameters["exchange"])
    queue = channel.queue_declare(queue=Parameters["queue"],exclusive=True)
    channel.queue_bind(exchange=Parameters["exchange"], queue=Parameters["queue"])
    print(' [*] Waiting for logs. To exit press CTRL+C')
    def callback(ch, method, properties, body):
        print("gata!")

    channel.basic_consume(queue=Parameters["queue"], on_message_callback=callback, auto_ack=True)

    channel.start_consuming()