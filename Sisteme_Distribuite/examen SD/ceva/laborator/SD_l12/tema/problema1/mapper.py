import requests


def mapper():
	# citesc mai multe url-uri dintr-un fisier
	f = open("url.txt", "r")
	sir = f.read()
	# le despart dupa '\n'
	urls = sir.split('\n')

	# lista de rezultate ce va fi returnata
	lista_url = []
	a = 0

	# parcurg lista de url-uri 
	for url in urls:
		# fac cererea pentru resursa respectiva
		x = requests.get(url)
		# separ rezultatele dupa '\n'
		content = str(x.text).split('\n')

		# parcurg linie cu linie si verific daca nu am ancora, in caz
		# afirmativ o salvez
		for c in content:

		    if 'href=' in c and '<a' in c:
		    	#print(c)
		    	sir = c.split('href=')
		    	link = sir[1].split('>')
		    	tupla = (url, link[0])
		    	lista_url.append(tupla)
		    	a = a + 1


	print(a)
	return lista_url

#mapper()
'''lista = mapper()
for i in lista:
	print(i[0])
	print("\t" + i[1])'''
