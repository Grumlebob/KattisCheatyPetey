#! /usr/bin/env python3

""" Generate random input for CheatyPetey
    Parameters:
    - loRandom, default 1
    - hiRandom, default 1.000.000
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
parser.add_argument("--loRandom", type=int, default=1, help="smallest integer")
parser.add_argument("--hiRandom", type=int, default=1000000, help="largest integer")
parser.add_argument("--loCards", type=int, default=1, help="smallest integer")
parser.add_argument("--hiCards", type=int, default=21, help="largest integer")
args = parser.parse_args()

random.seed(args.seed)


lowestRandom = args.loRandom
highestRandom = args.hiRandom

lowestCards = args.loCards
highestCards = args.hiCards

randomNumber = random.randrange(lowestRandom, highestRandom + 1)
while randomNumber % 2 == 0:
    randomNumber = random.randrange(lowestRandom, highestRandom + 1)
print(randomNumber)
amountOfCards = random.randrange(lowestCards, highestCards + 1)
print(amountOfCards)

uniqueNumbers = {}

while len(uniqueNumbers) < amountOfCards:
    uniqueNumbers[random.randrange(lowestCards, highestCards + 1)] = 1

for key in uniqueNumbers:
    print(key)


