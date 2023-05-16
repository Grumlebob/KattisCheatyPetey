#! /usr/bin/env python3
import copy
from collections import defaultdict


class Multiset:
    def __init__(self):
        self.items = defaultdict(lambda: 0)

    def add(self, item, amount):
        self.items[item] += amount

    def numberOf(self, item):
        return self.items[item]

    def remove(self, item, amount):
        if self.numberOf(item) <= amount:
            self.items.pop(item)
        else:
            self.items[item] -= amount

    def copy(self):
        return copy.deepcopy(self)

    def __eq__(self, other):
        if not isinstance(other, Multiset):
            return False
        return self.items == other.items

    def __hash__(self):
        return hash(tuple(sorted(self.items.items())))


MAX = float("inf")
MIN = -float("inf")

ruleCard = int(input())
target = ruleCard * 21
numberOfDifferentCardValues = int(input())

cardsAvailable = Multiset()
for _ in range(numberOfDifferentCardValues):
    cardsAvailable.add(int(input()), 1)


def bottumUp(cardSet: Multiset, target, isMostPlays=False, isPlaysLimited=False):  # limited cards only
    memoization = [MIN] * (target+1) if isMostPlays else [MAX] * (target+1)
    valuesAvailableAt: list[Multiset] = []
    for _ in range(target + 1):
        if not isPlaysLimited:
            break
        valuesAvailableAt.append(Multiset())
    memoization[0] = 0
    if isPlaysLimited:
        valuesAvailableAt[0] = cardSet
    for tempTarget in range(1, target + 1):
        for v, _ in cardSet.items.items():
            if v > tempTarget or isPlaysLimited and valuesAvailableAt[tempTarget - v].numberOf(v) == 0:
                continue
            if isMostPlays and memoization[tempTarget - v] + 1 > memoization[tempTarget] or not isMostPlays and memoization[tempTarget - v] + 1 < memoization[tempTarget]:
                memoization[tempTarget] = memoization[tempTarget - v] + 1
                if isPlaysLimited:
                    valuesAvailableAt[tempTarget] = valuesAvailableAt[tempTarget - v].copy()
                    valuesAvailableAt[tempTarget].remove(v, 1)
    return memoization[target]

# One of each card
if ruleCard == 3:
    result = bottumUp(cardsAvailable, target, False, True)
    print("Impossible" if result == MAX else result)

# Max 5 of each card
elif ruleCard == 5:
    for item, _ in cardsAvailable.items.items():
        cardsAvailable.add(item, 4)
    result = bottumUp(cardsAvailable, target, False, True)
    print("Impossible" if result == MAX else result)

# Max 6 of each card but isMostPlays possible
elif ruleCard == 6:
    for item, _ in cardsAvailable.items.items():
        cardsAvailable.add(item, 5)
    result = bottumUp(cardsAvailable, target, True, True)
    print("Impossible" if result == MIN else result)

# EVEN: highest amount of cards
elif ruleCard % 2 == 0:
    result = bottumUp(cardsAvailable, target, True, False)
    print("Impossible" if result == MIN else result)

# ODD: lowest amount of cards
elif ruleCard % 2 == 1:
    result = bottumUp(cardsAvailable, target, False, False)
    print("Impossible" if result == MAX else result)
