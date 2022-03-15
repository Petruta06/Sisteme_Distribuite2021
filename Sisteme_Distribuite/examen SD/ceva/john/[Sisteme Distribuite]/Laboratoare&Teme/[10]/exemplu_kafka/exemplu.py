from kafka import KafkaConsumer
from kafka import KafkaProducer
import threading

class Consumer(threading.Thread):
    def __init__(self, topic):
        super().__init__()
        self.topic = topic

    def run(self) -> None:
        consumer = KafkaConsumer(self.topic, auto_offset_reset='earliest')
        #topicul va fi creat automat, daca nu exista deja

        #thread-ul consumator primeste mesaje din topic
        for msg in consumer:
            print(f'Am consumat mesajul :  {str(msg.value, encoding="utf-8")}')


class Producer(threading.Thread):
    def __init__(self, topic):
        super().__init__()
        self.topic = topic

    def run(self) -> None:
        producer = KafkaProducer()
        for i in range(10):
            message = f"mesaj {i}"

            #thread-ul producator trimite mesaje catre un topic
            producer.send(topic=self.topic, value=bytearray(message, encoding="utf-8"))
            print(f'Am produs mesajul : {message}')

            #metoda flush asigura batch-ului de mesaje produce
            producer.flush()


if __name__ == "__main__":
    # se creeaza 2 threaduri : unul pt. producatorul de mesaje si celalalt consumator
    topic = "topic_exemplu_python"
    producer_thread = Producer(topic)
    consumer_thread = Consumer(topic)

    producer_thread.start()
    consumer_thread.start()

    producer_thread.join()
    consumer_thread.join()