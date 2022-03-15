from mapper import *


lista = mapper()

def reduce():
	result = " "

	for c in lista:
		result = result +"\t" + c

	return result



print(reduce())