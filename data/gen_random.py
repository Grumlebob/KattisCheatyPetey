#! /usr/bin/env python3

""" Generate two random numbers in a given range.
    Paramters:
    - lo, default -1000
    - hi, default 1000
    - same, are x and y the same

    seed is typically provided behind the scenes by the generator script

    Example from the command line, note that 0 is the seed:
    > python gen_random_pulse.py --lo 0 --same 0
    43 43
"""

import random
from argparse import ArgumentParser

parser = ArgumentParser("Random input to skelet")
parser.add_argument("seed", type=int, help="seed")
parser.add_argument("--same", action="store_true", help="both integers are the same")
parser.add_argument("--lo", type=int, default=-1000, help="smallest integer")
parser.add_argument("--hi", type=int, default=1000, help="largest integer")
args = parser.parse_args()

random.seed(args.seed)


lo = args.lo
hi = args.hi

x = random.randrange(lo, hi + 1)
y = x if args.same else random.randrange(lo, hi + 1)
print(x, y)
