#! /usr/bin/env python3


import random
from argparse import ArgumentParser

parser = ArgumentParser("Random input to skelet")
parser.add_argument("--ruleCard", type=int, default=5, help="Rulecard")
parser.add_argument("--cardsAvailable", nargs="+", type=int, help="list of integers")

args = parser.parse_args()

ruleCard = args.ruleCard
print(ruleCard)

uniqueNumbers = args.cardsAvailable

numberOfCards = len(uniqueNumbers)-1
print(numberOfCards)

#The last element is is the seed, so we don't want to print that
for i in range(len(uniqueNumbers)-1):
    print(uniqueNumbers[i])

