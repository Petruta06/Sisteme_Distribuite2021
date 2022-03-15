'''
    Reducer-ul reuneste perechile primite de la mapper, obtinand astfel numarul de
    aparitii ale cuvintelor
        <cuvant_0, 2>
        <cuvant_1, 1>
        <cuvant_2, 1>
'''

import sys

current_letter = None
letter = None
word = None
current_words = []

for field in sys.stdin:
    field = field.strip()

    letter, word = field.split()
    if current_letter != letter:
        if current_letter:
            print("<%s -> %s>" %(current_letter, current_words))
        current_letter = letter
        current_words = []
    if word not in current_words:
        current_words.append(word)

if len(current_words) > 0:
    print("<%s -> %s>" %(current_letter, current_words))