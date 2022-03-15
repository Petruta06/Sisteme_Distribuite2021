

def map():
	tupla = []
	f = open("ceva.txt", "r")
	sir = f.read()
	print(sir)
	sir = sir.split(" ")

	for c in sir:
		tupla +=[(c, 1)]

	print(tupla)
	return tupla

'''def main():
	map()


main()'''

