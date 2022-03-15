#!/usr/bin/env python
"""mapper.py"""

import subprocess
import sys
import re

if len(sys.argv) > 2:
	print("Too much parameters!")
else:
	pattern = sys.argv[1]
	commandOutputBinary = subprocess.run(["ls", "-l"], capture_output=True).stdout
	commandOutputAscii = commandOutputBinary.decode('ascii')
	matching = re.findall(pattern,commandOutputAscii)
	for match in matching:
        print('%s\t%s' % (pattern, match))




