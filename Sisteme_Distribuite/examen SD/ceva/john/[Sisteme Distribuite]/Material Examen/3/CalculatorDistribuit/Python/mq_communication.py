import pika
from retry import retry

class RabbitMq:
    config = {
        'host': '0.0.0.0',
        'port': 5678,
        'username': 'user',
        'password': 'user',
        'exchange': 'calculatorapp.direct',
        'routing_key': 'calculatorapp.routingkey1',
        'queue': 'calculatorapp.queue'
    }

    credentials = pika.PlainCredentials(config['username'], config['password'])
    parameters = (pika.ConnectionParameters(host=config['host']),
                  pika.ConnectionParameters(port=config['port']),
                  pika.ConnectionParameters(credentials=credentials))

    def on_receive_message(self, blocking_channel, deliver, properties, message):
        result = message.decode('utf-8')
        blocking_channel.confirm_delivery()
        try:
            print(f"\n\nRezultatul operatiei : {result}")
        except Exception:
            print(f"A aparut urmatoarea exceptie : {Exception}")
        finally:
            blocking_channel.stop_consuming()

    @retry(pika.exceptions.AMQPConnectionError, delay = 5, jitter = (1, 3))
    def receive_message(self):
        with pika.BlockingConnection(self.parameters) as connection:
            with connection.channel() as channel:
                channel.basic_consume(self.config['queue'], self.on_receive_message)
                try:
                    channel.start_consuming()
                except pika.exceptions.ConnectionClosedByBroker:
                    print("Connection closed by broker.")
                except pika.exceptions.AMQPChannelError:
                    print("AMQP Channel Error")
                except KeyboardInterrupt:
                    print("Application closed.")

    def send_message(self, message):
        with pika.BlockingConnection(self.parameters) as connection :
            with connection.channel() as channel:
                self.clear_queue(channel)
                channel.basic_publish(exchange=self.config['exchange'],
                                      routing_key=self.config['routing_key'],
                                      body=message)
    def clear_queue(self, channel):
        channel.queue_purge(self.config['queue'])