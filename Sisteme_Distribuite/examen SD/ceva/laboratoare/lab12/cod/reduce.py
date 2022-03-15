
from mapper import *

dict1 = map()

dict_result = {}

def reduce():
	for c in dict1:
		print(c)
		print(dict_result)
		print(c[0] in dict_result)
		if c[0] in dict_result:
			dict_result[c[0]] = int(dict_result[c[0]]) + 1
		else:
			dict_result[c[0]] =  1
		print(dict_result[c[0]])
	print(dict_result)


reduce()