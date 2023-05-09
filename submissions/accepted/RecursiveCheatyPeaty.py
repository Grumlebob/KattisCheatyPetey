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


sys.setrecursionlimit(10**9)

MAX = float("inf")
MIN = -float("inf")

cachedAnswers = {}

ruleCard = int(input())
target = ruleCard * 21
numberOfDifferentCardValues = int(input())

valuesAvailable = Multiset()
for _ in range(numberOfDifferentCardValues):
    valuesAvailable.add(int(input()), 1)


def bestToTarget(availableValues: Multiset, target, worst=False):  # unlimited cards only
    if (availableValues, target) in cachedAnswers:
        return cachedAnswers[(availableValues, target)]
    if target == 0:
        return 0
    elif target < 0:
        return MIN if worst else MAX
    best = max if worst else min
    res = MIN if worst else MAX
    for v, _ in availableValues.items.items():
        newValues = availableValues
        pre = bestToTarget(newValues, target - v, worst)
        res = best(res, pre + 1)
    cachedAnswers[(availableValues, target)] = res
    return res


def bottumUp(availableValues: Multiset, target, worst=False):  # limited cards only
    result = [MIN] * (target+1) if worst else [MAX] * (target+1)
    valuesAvailableAt: list[Multiset] = []
    for _ in range(target + 1):
        valuesAvailableAt.append(Multiset())
    result[0] = 0
    valuesAvailableAt[0] = availableValues
    for tempTarget in range(1, target + 1):
        for v, _ in availableValues.items.items():
            if v > tempTarget or valuesAvailableAt[tempTarget - v].numberOf(v) == 0:
                continue
            if worst and result[tempTarget - v] + 1 > result[tempTarget] or not worst and result[tempTarget - v] + 1 < result[tempTarget]:
                result[tempTarget] = result[tempTarget - v] + 1
                valuesAvailableAt[tempTarget] = valuesAvailableAt[tempTarget - v].copy()
                valuesAvailableAt[tempTarget].remove(v, 1)
    return result[target]

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
    result = bestToTarget(valuesAvailable, target, True)
    print("Impossible" if result == MIN else result)

# ODD: lowest amount of cards
elif ruleCard % 2 == 1:
    result = bestToTarget(valuesAvailable, target)
    print("Impossible" if result == MAX else result)
