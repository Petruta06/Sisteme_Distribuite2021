import sys
import re

regex = None
regexPattern = '/(.+)/'

for line in sys.stdin:
    match = re.search(regexPattern, line)
    #if match != None:
    #        print(match.group(1))
    print(line, end = "")
