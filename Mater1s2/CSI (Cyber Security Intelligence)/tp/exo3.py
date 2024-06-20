def LtoN(a):
    return [ord(c) - ord('A') for c in a.upper()]

def NtoL(a):
    return "".join(chr(num + ord('A')) for num in a)

def sub_mod_26(t1, t2):
    n1 = LtoN(t1)
    n2 = LtoN(t2)
    return [(num1 - num2) % 26 for num1, num2 in zip(n1, n2)]

def add_mod_26(t1, diff):
    n1 = LtoN(t1)
    return NtoL([(num1 + d) % 26 for num1, d in zip(n1, diff)])

w1 = "HQQYAJT"
w2 = "RJAJPWG"
diff = sub_mod_26(w1, w2)
possible_pairs = []
    
with open('fr.txt', 'r') as file:
    words = [line.strip().upper() for line in file if len(line.strip()) == 7]
    words_set = set(words)  

for word1 in words:
    expected_word2 = add_mod_26(word1, diff)
    if expected_word2 in words_set:
        possible_pairs.append((word1, expected_word2))

for pair in possible_pairs:
    print(pair)





