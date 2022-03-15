import sys
import requests
import re

urlsPattern = 'href=("https?://[A-Za-z0-9\.\-/_%]+"|"/?[A-Za-z0-9\.\-/_%]+")'

for URL in sys.stdin:
    URL = URL.strip('\n')
    content = requests.get(URL)
    binaryContent = content.content
    asciiContent = binaryContent.decode("utf-8")

    anchors = re.findall(urlsPattern, asciiContent)
    for anchor in anchors:
        print('%s\t%s' % (URL, anchor))