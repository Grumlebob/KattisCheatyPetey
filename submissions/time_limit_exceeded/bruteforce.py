
from itertools import permutations


rule_card = int(input())
target = rule_card * 21
N = int(input())
cards = []
for _ in range(N):
    card_value = int(input())
    cards.append(card_value)

current = 0

if rule_card == 3:  # lowest amount used, one of each at most
    used = float("inf")
    usedAccepted = float("inf")
    
    for card1 in cards:
        current = 0
        for card2 in cards:
            if current + card2 <= target:
                current += card2
                used += 1
            if current == target:
                usedAccepted = min(used, usedAccepted)
                break
    if usedAccepted == float("inf"):
        print("impossible")
    else:
        print(usedAccepted)

# elif rule_card == 5:
#     for card1 in cards:
#         for card2 in cards:
#             for card3 in cards:
#                 for card4 in cards:
#                     for card5 in cards:
