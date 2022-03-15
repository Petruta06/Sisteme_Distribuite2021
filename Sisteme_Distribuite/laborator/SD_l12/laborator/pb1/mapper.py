

def mapper():
	f = open("in.txt", "r")
	s = f.read()
	lista = s.split(' ')
	result =[]

	for c in lista:
		result.append((c[0], c))

	return result


'''lista = mapper()
print(lista)'''