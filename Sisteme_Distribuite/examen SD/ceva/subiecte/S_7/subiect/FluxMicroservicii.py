from RabbitMqConnection import *

class Flux:
    def __init__(self, queue):
        self.logger = RabbitMqConsumer(queue)

    def run(self):
        while True:
            receiver_mess = None
            try:
                receiver_mess = self.logger.receive()
                f = open("out.txt", "a")
                f.write(receiver_mess+"\n")
            except Exception as e:
                print(e)

def main():
    flux = Flux("log.queue")
    flux.run()


if __name__ == '__main__':
    main()