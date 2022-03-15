import mapper as m 


lista = m.mapper()


def apartine(l, sir):
	print(l)
	print(sir)
	for i in range(0, len(l)):
		if(l[i] == sir):
			return i
	return -1


def reduce():
	result_l = []
	result_c = []
	for c in lista:
		print(c[0])
		i = apartine(result_l, c[0])
		print(i)
		if(i == -1):
			result_l.append(c[0])
			result_c.append([c[1]])
		else:
			result_c[i].append(c[1])

	result =[]
	for i in range(0, len(result_l)):
		result.append((result_l[i], result_c[i]))


	return result


l = reduce()
print(l)
