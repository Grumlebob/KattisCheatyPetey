# Read input values
rule_card = int(input())
N = int(input())
target = rule_card * 21
cards = []
for _ in range(N):
    card_value = int(input())
    cards.append(card_value)

cards.sort()

if rule_card % 2 == 0: # highest amount
    max = -1
    current = 0

    for card in cards:
        

    
