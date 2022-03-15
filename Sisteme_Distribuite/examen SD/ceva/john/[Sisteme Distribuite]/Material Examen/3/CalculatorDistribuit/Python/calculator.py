from mq_communication import RabbitMq
import time

if __name__ == "__main__":
    queue = RabbitMq()
    try:
        while True:
            expression = input("Introduceti operatia dorita : ")
            queue.send_message(expression)
            print("Se calculeaza.", end="")
            print('.', end = "")
            #time.sleep(0.5)
            print('.', end = "")
            #time.sleep(0.5)
            result = queue.receive_message()
            #time.sleep(1.5)
            print("\n\n")
    except KeyboardInterrupt as kint:
        print("Intreruperea fortata a programului!\nLa revedere!\n\n")
