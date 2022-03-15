

from rabbit import RabbitMq

class Aplicatie:
	def __init__(self):
		self.rabbit_mq = RabbitMq()

	def send_request(self, request):
		self.rabbit_mq.send_message(message=request)
		self.rabbit_mq.receive_message()

	def bucla(self):
		while 1: 
			i = int(input("Optiunea="))
			if i==1:
				print('gen A')
				self.send_request('regenerate_A')
			elif i==2:
				print('gen B')
				self.send_request('regenerate_B')
			elif i==3:
				print('calcul')
				self.send_request('compute')
			else: 
				print("optiune invalida")


def main():
	c = Aplicatie()
	c.bucla()

main()
	