import os
import sqlite3


def mapper_file():
	f = open("url.txt", "r")
	sir = f.read()
	# le despart dupa '\n'
	results = sir.split('\n')
	print(results[0])
	# scot fragmentele din url (cele in continuarea # si construiesc tuplele)
	results_f = []
	for c in results:
		#sir = c[0].split('.com/')
		results_f.append((c, 1))

	return results_f


def mapper_bd():
	# calea din fisier
	data_path = "/home/ana/.mozilla/firefox/8h93wk4j.default-esr"
	history_db = os.path.join(data_path, 'places.sqlite')
	  
	# conexiunea cu baza de date
	c = sqlite3.connect(history_db)
	  
	# pentru executarea interogarii
	cursor = c.cursor()
	select_statement = "select moz_places.title from moz_places;"
	cursor.execute(select_statement)
	  

	results = cursor.fetchall()
	cursor.close()

	# scot fragmentele din url (cele in continuarea # si construiesc tuplele)
	results_f = []
	for c in results:
		print(c[0])
		#sir = c[0].split('.com/')
		#print(sir[0])
		results_f.append((c[0], 1))

	#print(results_f)
	return results_f


r = mapper_bd()

'''for c in r:
	print('<' + c[0] +', ' +str(c[1])+'>')'''

