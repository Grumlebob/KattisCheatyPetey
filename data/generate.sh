#!/bin/bash

# run this as ./generate.sh from the data directory. It populates data/secret

. ../../_testdata_tools/gen.sh

use_solution th.py # Use submissions/accepted/th-ac.py to generate answers

# all the generators are introduced here
compile gen_random.py
compile gen_echo.py


# there are two *.in files in data/sample
samplegroup
sample 1
sample 2

# First test group, and the number of points for it
group group1 50
limits --same # the 'same' argument is passed to input_validators/validate.py
tc 1 # data/sample/2.in belongs here
tc smallpos-s     gen_random --lo 1 --hi 9 --same
tc smallneg-s     gen_random --lo -9 --hi -1 --same
tc small-s         gen_random --lo -9 --hi 9 --same
tc random-pos-s gen_random --lo 1 --same
tc random-neg-s   gen_random --hi -1 --same
tc random-1-s      gen_random --same
tc random-2-s      gen_random --same
tc random-3-s      gen_random --same
tc fortytwo 	gen_echo 42 42
tc zero 	gen_echo 0 0

# Second test group, and the number of points for it
group group2 50
include_group group1 # group2 includes group1
tc 2
tc smallpos     gen_random --lo 1 --hi 9 
tc smallneg     gen_random --lo -9 --hi 1
tc small        gen_random --lo -9 --hi 9
tc random-pos-1 gen_random --lo 1 
tc random-neg-1 gen_random --hi -1
tc random-1      gen_random 
tc random-2      gen_random 
tc random-3      gen_random 
tc edge-1 	gen_echo 0 1000
tc edge-2 	gen_echo -1000 1000
tc edge-3 	gen_echo 1000 -1000
tc edge-4 	gen_echo -1000 -1000
