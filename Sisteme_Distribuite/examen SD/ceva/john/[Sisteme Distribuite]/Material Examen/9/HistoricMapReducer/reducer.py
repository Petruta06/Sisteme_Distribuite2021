import sys

current_host = None
host = None
current_frequency = 0

for line in sys.stdin:
    line = line[line.find('<') + 1 : line.find('>')]
    host, frequency = line.split()

    try:
        frequency = int(frequency)
    except ValueError:
        continue

    if current_host != host:
        if current_host:
            print('%s -> %s' %(current_host, current_frequency))
        current_frequency = frequency
        current_host = host
    else:
        current_frequency = current_frequency + frequency

if current_frequency > 0:
    print('%s -> %s' %(current_host, current_frequency))
