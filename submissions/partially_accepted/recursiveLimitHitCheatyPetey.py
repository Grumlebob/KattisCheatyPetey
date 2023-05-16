#! /usr/bin/env python3
import sys
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

cachedAnswers = {}

ruleCard = int(input())
target = ruleCard * 21
numberOfDifferentCardValues = int(input())

valuesAvailable = Multiset()
for _ in range(numberOfDifferentCardValues):
    valuesAvailable.add(int(input()), 1)


def calculateBestPlays(cardSet: Multiset, target, isMostPlays=False):  # unlimited cards only
    if (cardSet, target) in cachedAnswers:
        return cachedAnswers[(cardSet, target)]
    if target == 0:
        return 0
    elif target < 0:
        return MIN if isMostPlays else MAX
    best = max if isMostPlays else min
    res = MIN if isMostPlays else MAX
    for v, _ in cardSet.items.items():
        newValues = cardSet
        pre = calculateBestPlays(newValues, target - v, isMostPlays)
        res = best(res, pre + 1)
    cachedAnswers[(cardSet, target)] = res
    return res


def bottumUp(cardSet: Multiset, target, isMostPlays=False):  # limited cards only
    memoization = [MIN] * (target+1) if isMostPlays else [MAX] * (target+1)
    valuesAvailable: list[Multiset] = []
    for _ in range(target + 1):
        valuesAvailable.append(Multiset())
    memoization[0] = 0
    valuesAvailable[0] = cardSet
    for tempTarget in range(1, target + 1):
        for v, _ in cardSet.items.items():
            if v > tempTarget or valuesAvailable[tempTarget - v].numberOf(v) == 0:
                continue
            if isMostPlays and memoization[tempTarget - v] + 1 > memoization[tempTarget] or not isMostPlays and memoization[tempTarget - v] + 1 < memoization[tempTarget]:
                memoization[tempTarget] = memoization[tempTarget - v] + 1
                valuesAvailable[tempTarget] = valuesAvailable[tempTarget - v].copy()
                valuesAvailable[tempTarget].remove(v, 1)
    return memoization[target]

# One of each card
if ruleCard == 3:
    result = bottumUp(valuesAvailable, target)
    print("Impossible" if result == MAX else result)

# Max 5 of each card
elif ruleCard == 5:
    for item, _ in valuesAvailable.items.items():
        valuesAvailable.add(item, 4)
    result = bottumUp(valuesAvailable, target)
    print("Impossible" if result == MAX else result)

# Max 6 of each card but worst possible
elif ruleCard == 6:
    for item, _ in valuesAvailable.items.items():
        valuesAvailable.add(item, 5)
    result = bottumUp(valuesAvailable, target, True)
    print("Impossible" if result == MIN else result)

# EVEN: highest amount of cards
elif ruleCard % 2 == 0:
    result = calculateBestPlays(valuesAvailable, target, True)
    print("Impossible" if result == MIN else result)

# ODD: lowest amount of cards
elif ruleCard % 2 == 1:
    result = calculateBestPlays(valuesAvailable, target)
    print("Impossible" if result == MAX else result)
