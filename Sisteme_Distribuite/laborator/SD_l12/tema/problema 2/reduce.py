import exe as m

lista = m.mapper_bd()

def apartine(l, sir):
	for i in range(0, len(l)):
		if(l[i]==sir):
			return i
	return -1




def reduce():
	result_u = []
	result_c = []

	for e in lista:

		i = apartine(result_u, e[0]) 
		if i == -1:
			result_u.append(e[0])
			result_c.append(1)
		else:
			result_c[i] = result_c[i] + 1



	result = []
	for i in range(0, len(result_u)):
		result.append((result_u[i],result_c[i]) )
	return result




r = reduce()
print(r)

for c in r:
	print('<' + c[0] +'>')
