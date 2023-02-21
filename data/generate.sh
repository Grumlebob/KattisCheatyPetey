#!/bin/bash

USE_SCORING=0 #Pass-fail problem.

# run this as ./generate.sh from the data directory. It populates data/secret

. ../../_testdata_tools/gen.sh

use_solution MainCheatyPetey.java # Use submissions/accepted/

# all the generators are introduced here
compile gen_random.py
compile gen_explicit.py


# there are two *.in files in data/sample
sample 1
sample 2
sample 3
sample 4

# generate tests here
#tc 1 # data/sample/2.in belongs here
tc smallNumbers0 gen_random --loRandom 1 --hiRandom 2 --loCards 1 --hiCards 5
#tc smallNumbers1 gen_random --loRandom 5 --hiRandom 8 --loCards 6 --hiCards 11
#tc smallNumbers2 gen_random --loRandom 9 --hiRandom 13 --loCards 12 --hiCards 21
tc highNumbers0 gen_random --loRandom 1 --hiRandom 1000000 --loCards 1 --hiCards 21
#tc highNumbers1 gen_random --loRandom 1 --hiRandom 1000000 --loCards 10 --hiCards 21
#tc highNumbers2 gen_random --loRandom 1 --hiRandom 1000000 --loCards 5 --hiCards 15
#tc highNumbers3 gen_random --loRandom 1 --hiRandom 1000000 --loCards 1 --hiCards 21
#tc fortytwo 	gen_echo 42 42   d
#tc zero 	gen_echo 0 0
#tc edgecase1 generate explicit 0 0 0
#tc edgecase2 generate explicit 0 0 1

