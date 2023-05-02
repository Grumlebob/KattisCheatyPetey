from itertools import combinations_with_replacement


rule_card = int(input())
target = rule_card * 21
N = int(input())
cards = []
# load cards into list
min_card = float("inf")
for _ in range(N):
    card_value = int(input())
    min_card = min(min_card, card_value)
    cards.append(card_value)
cards.sort()

# def min_used(target, cards, used, i, max_allowed=float("inf")):
#     for p in filter_combinations(cards, i, max_allowed):
#         print(p)
#         current = 0
#         for card in p:
#             if current + card <= target:
#                 current += card
#                 used += 1
#             if current == target:
# print(len(i))


def filter_combinations(lst: tuple, n, max_count: int):
    for p in combinations_with_replacement(lst, n):
        if all(p.count(x) <= max_count for x in set(p)):
            yield p


def min_used2(target, cards, i, max_allowed=float("inf")):
    for p in filter_combinations(cards, i, max_allowed):
        if (min_card * len(p) > target):
            print("Impossible")
            exit()
        # print(p, sum(p))
        if sum(p) == target:
            print(p, len(p))
            exit()

if rule_card == 3:
    # lowest amount used, one of each at most
    used = float("inf")
    for i in range(1, len(cards)+1):
        # min_used(target, cards, used, i, 3)
        min_used2(target, cards, i, 1)
        print(i, used)
    if used == float("inf"):
        print("Impossible")
    # else:
    #     print(used)
elif rule_card == 5:
    # lowest amount used, at most 5 of each
    for i in range(len(cards)*5):
        # min_used(target, cards, used, i, 5)
        min_used2(target, cards, i, 5)  # if a number is found, it will exit
    print("Impossible")
# elif rule_card % 2 == 1:
#     for i in range(10000):
#         min_used2(target, cards, i)
#     print("Impossible")
elif rule_card % 2 == 1:
    used = float("inf")
    for i in range(10000):
        # min_used(target, cards, used, i, 5)
        min_used2(target, cards, i)
        print(i, used)
    if used == float("inf"):
        print("Impossible")
