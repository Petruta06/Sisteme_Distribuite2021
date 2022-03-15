'''
    Mapper-ul sparge efectiv fisierele text in perechi de forma:
        <cuvant_0, 1>
        <cuvant_1, 1>
        <cuvant_2, 1>
        <cuvant_0, 1>
'''


import sys

try:
    for line in sys.stdin:
        # eliminam spatiile multiple
        line = line.strip()

        # obtinem cuvintele prin split dupa spatiu
        words = line.split()

        for word in words:
            print('%s\t%s' % (word, 1))
except KeyboardInterrupt:
    print("Execution end!")