#! /usr/bin/env python3

from itertools import combinations_with_replacement


ruleCard = int(input())
target = ruleCard * 21
N = int(input())
cardSet = []
# load cards into list
minCard = float("inf")
for _ in range(N):
    cardValue = int(input())
    minCard = min(minCard, cardValue)
    cardSet.append(cardValue)
cardSet.sort()


def filterCombinations(lst, n, maxCount):
    for p in combinations_with_replacement(lst, n):
        if all(p.count(x) <= maxCount for x in set(p)):
            yield p


def minUsed(target, cards, i, maxAllowed=float("inf")):
    for p in filterCombinations(cards, i, maxAllowed):
        if (minCard * len(p) > target):
            print("Impossible")
            exit()

        if sum(p) == target:
            print(len(p))
            exit()


def maxUsed(target, cards, i, maxAllowed=float("inf")):
    for p in filterCombinations(cards, i, maxAllowed):
        if (minCard * len(p) < target):
            print("Impossible")
            exit()
        
        if sum(p) == target:
            print(len(p))
            exit()


def maxUsed(target, cards, i, maxAllowed=float("inf")):
    for p in filterCombinations(cards, i, maxAllowed):
        if (minCard * len(p) < target):
            print("Impossible")
            exit()
        
        if sum(p) == target:
            print(len(p))
            exit()


if ruleCard == 3:
    # lowest amount used, one of each at most
    for i in range(1, len(cardSet)+1):
        minUsed(target, cardSet, i, 1)
    print("Impossible")
elif ruleCard == 5:
    # lowest amount used, at most 5 of each
    for i in range(len(cardSet)*5):
        minUsed(target, cardSet, i, 5)
    print("Impossible")
elif ruleCard % 2 == 1:
    for i in range(10000):
        minUsed(target, cardSet, i)
        i += 1
    print("Impossible")
elif ruleCard == 6:
    for use in range(target//minCard+1, 0, -1):
        maxUsed(target, cardSet, use, 6)
    print("Impossible")
elif ruleCard % 2 == 0:
    for use in range(target//minCard, 0, -1):
        maxUsed(target, cardSet, use)
    print("Impossible")
