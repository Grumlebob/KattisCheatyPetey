#! /usr/bin/env python3
import sys
import re
import argparse

parser = argparse.ArgumentParser("blabla")
parser.add_argument("--same", action="store_true", help="both integers are the same")
args = parser.parse_args()

firstline = sys.stdin.readline()

assert re.match("(0|-?[1-9][0-9]*) (0|-?[1-9][0-9]*)\n", firstline)
x, y = map(int, firstline.split())
assert -1000 <= x <= 1000
assert -1000 <= y <= 1000
if args.same:
    assert x == y

assert sys.stdin.readline() == ""

sys.exit(42) # we're happy
