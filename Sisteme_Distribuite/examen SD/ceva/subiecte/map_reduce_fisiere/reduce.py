from mapper import *

lista = mapper()

def reduce():
	result = { }

	for c in lista:
		# verific mai intai ca acest cuvant nu este deja in dict
		if c[0] in result:
			# verific daca nu mai am indicele o data
			if c[1] in result[c[0]]:
				result[c[0]][c[1]] = result[c[0]][c[1]] +1
			else:
				result[c[0]][c[1]] = 1
		else: 
			result[c[0]] = {c[1]: 1}

	print(result)

reduce()