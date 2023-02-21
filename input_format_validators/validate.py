#! /usr/bin/env python3
import sys
import re
import argparse

parser = argparse.ArgumentParser("blabla")
parser.add_argument("--same", action="store_true", help="both integers are the same")
args = parser.parse_args()

randomNumber = input()
amountOfCards = input()

assert re.match(r"\d+", randomNumber)
assert re.match(r"\d+", amountOfCards)

randomNumber = int(randomNumber)
amountOfCards = int(amountOfCards)

assert 1 <= randomNumber <= 1000000
assert 1 <= amountOfCards <= 21

for i in range(amountOfCards):
    card = input()
    assert re.match(r"\d+", card)
    card = int(card)
    assert 1 <= card <= 21


assert sys.stdin.readline() == "" #This asserts that there is no more input

sys.exit(42) # we're happy
