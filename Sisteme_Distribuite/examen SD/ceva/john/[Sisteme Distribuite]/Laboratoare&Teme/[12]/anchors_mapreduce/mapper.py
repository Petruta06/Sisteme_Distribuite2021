#!/usr/bin/env python
"""mapper.py"""

import requests
import re
import sys

for URL in sys.stdin:
    content = requests.get(URL)
    binaryContent = content.content
    asciiContent = binaryContent.decode('utf-8')
    pattern = 'href=("https?://[A-Za-z0-9\.\-/_%]+"|"/?[A-Za-z0-9\.\-/_%]+")'
    anchors = re.findall(pattern, asciiContent)
    for anchor in anchors:
        print('%s\t%s' % (URL, anchor))
