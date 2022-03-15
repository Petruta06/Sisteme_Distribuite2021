import sys

for words in sys.stdin:
    words = words.strip()

    items = words.split()

    for item in items:
        print('%c\t%s' %(item[0], item))