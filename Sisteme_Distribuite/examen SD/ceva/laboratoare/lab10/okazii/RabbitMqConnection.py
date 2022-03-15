import pika
from retry import retry

class RabbitMqInterface:
    def __init__(self):
        self.config = {
            'host': '0.0.0.0',
            'port': 5678,
            'username': 'student',
            'password': 'student'
        }
        self.credentials = pika.PlainCredentials(self.config['username'],
                                            self.config['password'])
        self.parameters = (pika.ConnectionParameters(host=self.config['host']),
                      pika.ConnectionParameters(port=self.config['port']),
                      pika.ConnectionParameters(credentials=self.credentials))

class RabbitMqProducer(RabbitMqInterface):
    def __init__(self, exchange, routing_key):
        super().__init__()
        self.config["exchange"] = exchange
        self.config["routing_key"] = routing_key

    def send_message(self, message):
        with pika.BlockingConnection(self.parameters) as connection:
            # automatically close the channel
            with connection.channel() as channel:

                channel.basic_publish(
                    exchange=self.config['exchange'],
                    routing_key=self.config['routing_key'],
                    body=message)


class RabbitMqConsumer(RabbitMqInterface):
    def __init__(self,  queue):
        super().__init__()
        self.config["queue"] = queue
        self.connection = pika.BlockingConnection(self.parameters)
        self.channel = self.connection.channel()
        self.channel.queue_purge(self.config["queue"])

    @retry(pika.exceptions.AMQPConnectionError, delay=5, jitter=(1, 3))
    def receive(self):
        try:
            result_msg = self.channel.basic_get(self.config['queue'])
            if result_msg[2]:
                self.channel.basic_ack(result_msg[1].delivery_tag)
                return result_msg[2].decode('utf-8')
            else:
                raise Exception("reading try failed")


        except pika.exceptions.ConnectionClosedByBroker:
            print("Connection closed by broker.")
            # Don't recover on channel errors
        except pika.exceptions.AMQPChannelError:
            print("AMQP Channel Error")
            # Don't recover from KeyboardInterrupt
        except KeyboardInterrupt:
            print("Application closed.")