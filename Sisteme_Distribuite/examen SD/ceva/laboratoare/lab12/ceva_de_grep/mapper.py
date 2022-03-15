
import re

def mapper():
	f = open("fisier.txt", "r")
	regex = "^[a-z]"

	sir = f.read()
	sir = sir.split(" ")
	print(sir)

	result= []

	for c in sir:
		print(c)
		if re.search(regex, c):
			result += [c]

	return result


