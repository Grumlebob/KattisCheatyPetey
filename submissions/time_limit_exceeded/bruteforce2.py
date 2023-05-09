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


def filter_combinations(lst: tuple, n, max_count: int):
    for p in combinations_with_replacement(lst, n):
        if all(p.count(x) <= max_count for x in set(p)):
            yield p


def min_used(target, cards, i, max_allowed=float("inf")):
    for p in filter_combinations(cards, i, max_allowed):
        if (min_card * len(p) > target):
            print("Impossible")
            exit()
        # print(p, sum(p))
        if sum(p) == target:
            print(len(p))
            exit()


def max_used(target, cards, i, max_allowed=float("inf")):
    for p in filter_combinations(cards, i, max_allowed):
        if (min_card * len(p) < target):
            print("Impossible")
            exit()
        # print(p, sum(p))
        if sum(p) == target:
            # print(p, len(p))
            print(len(p))
            exit()


def max_used(target, cards, i, max_allowed=float("inf")):
    for p in filter_combinations(cards, i, max_allowed):
        if (min_card * len(p) < target):
            print("Impossible")
            exit()
        # print(p, sum(p))
        if sum(p) == target:
            # print(p, len(p))
            print(len(p))
            exit()


if rule_card == 3:
    # lowest amount used, one of each at most
    for i in range(1, len(cards)+1):
        min_used(target, cards, i, 1)
    print("Impossible")
elif rule_card == 5:
    # lowest amount used, at most 5 of each
    for i in range(len(cards)*5):
        min_used(target, cards, i, 5)
    print("Impossible")
elif rule_card % 2 == 1:
    for i in range(10000):
        min_used(target, cards, i)
        i += 1
    print("Impossible")
elif rule_card == 6:
    for use in range(target//min_card+1, 0, -1):
        # print(use)
        max_used(target, cards, use, 6)
    print("Impossible")
elif rule_card % 2 == 0:
    for use in range(target//min_card, 0, -1):
        # print(use)
        max_used(target, cards, use)
    print("Impossible")
