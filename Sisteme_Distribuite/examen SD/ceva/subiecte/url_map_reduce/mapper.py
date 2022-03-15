import requests
import sys
from bs4 import BeautifulSoup

lista_url = ["https://www.w3schools.com/python/trypython.asp?filename=demo_regex",
			"https://www.w3schools.com/python/trypython.asp?filename=demo_regex"]

blacklist = [
    '[doument]',
    'script',
    'header',
    'html',
    'meta',
    'head',
    'input',
    'style'
]


def mapper():
	

	for url in lista_url:
		words=""
		sir = BeautifulSoup(requests.get(url).content, "html.parser").find_all(text=True)
		for c in sir:
			if c.parent.name not in blacklist:
				words += '{}'.format(c)

		words = words.split()

		for c in words:
			print("%s, {%s: %s}"% (c, url, 1))
		



mapper()
