#!/usr/bin/env python
"""reducer.py"""

import sys

current_global_url = None
current_internal_urls = []
internal_url = None

for line in sys.stdin:
    global_url, internal_url = line.split('\t')

    if current_global_url != global_url:
        if current_internal_urls:
            print('%s\t[%s]' % (current_global_url, current_internal_urls))
        current_global_url = global_url
        current_internal_urls = []
    current_internal_urls.append(internal_url)

if current_internal_urls:
    print("%s\t%s" % (current_global_url, current_internal_urls))
