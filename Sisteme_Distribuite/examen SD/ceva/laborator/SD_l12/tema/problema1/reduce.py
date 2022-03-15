import mapper as m

# preiau rezultatele de la mapper 
lista =m.mapper()

# fct careb imi verifca daca url este deja in lista
def apartine(sir, lista):
	for i in range (0, len(lista)):
		#print(result_url[i])
		#print(sir)
		if lista[i] == sir:
			return i

	return -1

# listele in care voi returna rezultele 
result_url = []
result_url_intern =[]

# lista cu tuple
result = []

print(lista)
for c in lista:
	i = apartine(c[0], result_url)
	print(i)
	if  i == -1:
		print('nu este in lista si adaug')
		result_url.append(c[0])
		result_url_intern.append([c[1]])
	else:
		print('este deja in lista si inserez doar tupla')
		result_url_intern[i].append(c[1])


# pun in tupla
for i in range (0, len(result_url)):
	result.append((result_url[i], result_url_intern[i]))


f = open("out.txt", "a")
for c in result:
	f.write("\n\n*******************************************\n" + c[0])
	for l in c[1]:
		f.write("\n\t" + l)


f.close()
