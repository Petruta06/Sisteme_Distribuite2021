import sys
import re

regexPattern = '((https|http)://|file:///)([A-z0-9\.\-:]+)/'

def getHost(url):
    match = re.findall(regexPattern, url)
    completeScheme = match[0]
    return completeScheme[2]


if __name__ == "__main__":
    for line in sys.stdin:
        if line != "\n":
            frequency, url = line.split('|', maxsplit=1)
            host = getHost(url)
            print('<%s\t%s>' %(host, frequency))