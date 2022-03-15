

# lista de fisiere

list_file = ["f1.txt", "f2.txt", "f3.txt"]

def mapper():
	result = []
	for name in list_file:
		f = open(name, "r")
		sir = f.read().split(" ")

		for cuvant in sir:
			result += [(cuvant, name, 1)]

	return result

#print(mapper())