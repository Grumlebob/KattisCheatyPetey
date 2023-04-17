#! /usr/bin/env python3

""" Generate random input for CheatyPetey
    Parameters:
    - loCards, default 1
    - hiCards, default 21

    seed is typically provided behind the scenes by the generator script

    Example from the command line, note that 0 is the seed:
    > python gen_random.py --loRandom 2 --hiRandom 50 --loCards 1 --hiCards 21 0
    43 43
"""

import random
from argparse import ArgumentParser

parser = ArgumentParser("Random input to skelet")
parser.add_argument("seed", type=int, help="seed")
parser.add_argument("--loCards", type=int, default=1, help="smallest integer")
parser.add_argument("--hiCards", type=int, default=21, help="largest integer")
parser.add_argument("--ruleCard", type=int, default=5, help="Rulecard")
args = parser.parse_args()

random.seed(args.seed)

lowestCards = args.loCards
highestCards = args.hiCards

ruleCard = args.ruleCard
print(ruleCard)

amountOfCards = random.randrange(lowestCards, highestCards + 1)
print(amountOfCards)

uniqueNumbers = {}

while len(uniqueNumbers) < amountOfCards:
    uniqueNumbers[random.randrange(lowestCards, highestCards + 1)] = 1

for key in uniqueNumbers:
    print(key)


