import sys

d= {}

for line in sys.stdin:
	line = line.strip()
	word, url, count = line.split()
	word = word[0:-1]
	url = url[1:-1]
	count = count [0:-1]

	if word in d:
		if url in d[word]:
			d[word][url] = int(d[word][url]) +1
		else:
			d[word][url] = 1
	else:
		d[word] ={url: 1}

print(d)

# python3 mapper.py | python3 reduce.py

