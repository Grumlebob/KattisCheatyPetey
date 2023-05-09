#! /usr/bin/env python3

import random
from argparse import ArgumentParser

parser = ArgumentParser("Random input to skelet")
parser.add_argument("seed", type=int, help="seed")
parser.add_argument("--loRuleCard", type=int, default=1, help="smallest integer")
parser.add_argument("--hiRuleCard", type=int, default=1000000, help="largest integer")
parser.add_argument("--loCards", type=int, default=1, help="smallest integer")
parser.add_argument("--hiCards", type=int, default=21, help="largest integer")
args = parser.parse_args()

random.seed(args.seed)


lowestRandom = args.loRuleCard
highestRandom = args.hiRuleCard

lowestCards = args.loCards
highestCards = args.hiCards

randomNumber = random.randrange(lowestRandom, highestRandom + 1)
print(randomNumber)
amountOfCards = random.randrange(lowestCards, highestCards + 1)
print(amountOfCards)

uniqueNumbers = {}

while len(uniqueNumbers) < amountOfCards:
    uniqueNumbers[random.randrange(1, highestCards + 1)] = 1

for key in uniqueNumbers:
    print(key)


