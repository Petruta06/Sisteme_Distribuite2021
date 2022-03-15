#!/usr/bin/env python
"""reducer.py"""

import sys

lines = []
pattern = None
for line in sys.stdin:
    line = line.strip()
    pattern, found = line.split('\t')
    lines.append(found)

print('%s : %s' % (pattern, ' '.join(lines)))










