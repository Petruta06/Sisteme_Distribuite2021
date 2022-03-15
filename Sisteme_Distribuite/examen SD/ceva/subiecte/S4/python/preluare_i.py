
import requests
import socket
import time

HOST = "localhost"
SERVER_PORT = 1600

class Server:
	def run_server(self):
		sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		sock.bind((HOST, SERVER_PORT))
		# astept sa se conecteze clientii
		print("Astept sa se conectez macar un client!")
		sock.listen()
		conn, addr = sock.accept()
		while True:
			# cer resursa 
			r = requests.get('https://www.metaweather.com/api/location/search/?query=New%20York')
			#print(r.json()) # o transform in json
			conn.sendall(bytes(r.text, 'utf-8'))
			time.sleep(3)




def main():
	server = Server()
	
	server.run_server()

if __name__ == '__main__':
	main()