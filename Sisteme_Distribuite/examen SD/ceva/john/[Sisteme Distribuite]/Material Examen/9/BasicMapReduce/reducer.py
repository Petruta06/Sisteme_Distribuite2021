'''
    Reducer-ul reuneste perechile primite de la mapper, obtinand astfel numarul de
    aparitii ale cuvintelor
        <cuvant_0, 2>
        <cuvant_1, 1>
        <cuvant_2, 1>
'''

import sys

current_word = None
current_count = 0
word = None

wordMapping = {}

for line in sys.stdin:
    # eliminam spatiile multiple
    line = line.strip()

    # obtinem cuvantul si frecventa printr-un split dupa spatiu, [cuvant frecventa]
    word, count = line.split()

    try:
        count = int(count)
    except ValueError:
        continue

    if current_word != word:
        if current_word:
            print('%s\t%s' % (current_word, current_count))
        current_count = count
        current_word = word
    else:
        current_count += count

if current_count > 0:
    print('%s\t%s' % (current_word, current_count))
