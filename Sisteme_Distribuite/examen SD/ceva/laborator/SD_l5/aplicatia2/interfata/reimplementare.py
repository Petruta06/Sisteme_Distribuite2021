import functools
import json
import pika
from retry import retry



class RabbitMqR:
    config = {
        'host': '0.0.0.0',
        'port': 5678,
        'username': 'student',
        'password': 'student',
        'exchange': 'libraryapp.direct',
        'routing_key': 'libraryapp.routingkey1',
        'queue': 'libraryapp.queue'
    }
    credentials = pika.PlainCredentials(config['username'], config['password'])
    parameters = (pika.ConnectionParameters(host=config['host']),
                  pika.ConnectionParameters(port=config['port']),
                  pika.ConnectionParameters(credentials=credentials))

    def __init__(self, ui):
        self.ui = ui
        self._connection = None
        self.channel = None

        self.acked = None
        self.nacked = None
        #self._url = amqp_url

        self.stop = False

    def connection(self):
        return pika.SelectConnection(

        )
    def on_connection_open(self):
        self.open_channel()

    def on_connection_open_error(self, err):
        self._connection.ioloop.call_later(5, self.connection.ioloop.stop)

    def on_connection_closed(self, reason):
        self.channel = None

        if self.stop:
            self._connection.ioloop.stop()
        else:
            self._connection.ioloop.call_later(5,  self._connection.ioloop.stop)


    def open_channel(self):
        self._connection.channel(on_open_callback =self.on_open_channel)

    def on_open_channel(self, channel):
        self.channel = channel
        self.add_on_channe_close_callback()
        self.setup.exchange(RabbitMqR.config['exchange'])

    def add_on_channel_close_callback(self):
        self.channel.add_on_close_callback(self.on_channel_closed)

    def on_channel_closed(self, channel):
        self._channel = None
        if not self._stopping:
            self._connection.close()

    def setup_exchange(self, exchange_name):


        # Note: using functools.partial is not required, it is demonstrating
        # how arbitrary data can be passed to the callback when it is called
        cb = functools.partial(
            self.on_exchange_declareok, userdata=exchange_name)
        self._channel.exchange_declare(
            exchange=exchange_name,
            exchange_type=self.EXCHANGE_TYPE,
            callback=cb)

    def on_exchange_declareok(self, _unused_frame):
        self.setup_queue(self.QUEUE)

    def setup_queue(self, queue_name):
        self._channel.queue_declare(
            queue=queue_name, callback=self.on_queue_declareok)

    def on_queue_declareok(self, _unused_frame):

        self._channel.queue_bind(
            self.QUEUE,
            self.EXCHANGE,
            routing_key=self.ROUTING_KEY,
            callback=self.on_bindok)

    def on_bindok(self, _unused_frame):

        self.start_publishing()

    def start_publishing(self):
        self.enable_delivery_confirmations()
        self.schedule_next_message()

    def enable_delivery_confirmations(self):
        self._channel.confirm_delivery(self.on_delivery_confirmation)

    def on_delivery_confirmation(self, method_frame):

        confirmation_type = method_frame.method.NAME.split('.')[1].lower()
        if confirmation_type == 'ack':
            self._acked += 1
        elif confirmation_type == 'nack':
            self._nacked += 1
        self._deliveries.remove(method_frame.method.delivery_tag)


    def schedule_next_message(self):

        self._connection.ioloop.call_later(self.PUBLISH_INTERVAL,
                                           self.publish_message)

    def publish_message(self):

        if self._channel is None or not self._channel.is_open:
            return

        hdrs = {u'مفتاح': u' قيمة', u'键': u'值', u'キー': u'値'}
        properties = pika.BasicProperties(
            app_id='example-publisher',
            content_type='application/json',
            headers=hdrs)

        message = u'مفتاح قيمة 键 值 キー 値'
        self._channel.basic_publish(self.EXCHANGE, self.ROUTING_KEY,
                                    json.dumps(message, ensure_ascii=False),
                                    properties)
        self._message_number += 1
        self._deliveries.append(self._message_number)

        self.schedule_next_message()

    def run(self):
        """Run the example code by connecting and then starting the IOLoop.
        """
        while not self._stopping:
            self._connection = None
            self._deliveries = []
            self._acked = 0
            self._nacked = 0
            self._message_number = 0

            try:
                self._connection = self.connect()
                self._connection.ioloop.start()
            except KeyboardInterrupt:
                self.stop()
                if (self._connection is not None and
                        not self._connection.is_closed):
                    # Finish closing
                    self._connection.ioloop.start()


    def stop(self):

        self._stopping = True
        self.close_channel()
        self.close_connection()

    def close_channel(self):

        if self.channel is not None:
            self.channel.close()

    def close_connection(self):

        if self._connection is not None:

            self._connection.close()

