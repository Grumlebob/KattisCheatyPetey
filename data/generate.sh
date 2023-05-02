#!/bin/bash

# USE_SCORING=100 #Pass-fail problem.
# run this as ./generate.sh from the data directory. It populates data/secret

. ../../_testdata_tools/gen.sh

use_solution MainCheatyPetey.java # Use submissions/accepted/

# all the generators are introduced here
compile gen_random.py
compile gen_explicit.py
compile gen_even_rulecard.py
compile gen_odd_rulecard.py
compile gen_Specific_rulecard.py
compile gen_Specific_case.py

# there are two *.in files in data/sample
samplegroup
sample 1
sample 2
sample 3
sample 4
sample 5
sample 6

# generate tests here
#tc 1 # data/sample/2.in belongs here


group RuleCardOne 14

tc DPandGreedyGetsDifferentResultForOne gen_Specific_case --ruleCard 1 --cardsAvailable 1 5 8 17
tc PossibleSolutionForOne gen_Specific_case --ruleCard 1 --cardsAvailable
tc ImpossibleSolutionForOne gen_Specific_case --ruleCard 1 --cardsAvailable

group RuleCardOdd 14

tc DPandGreedyGetsDifferentResultForOdd gen_Specific_case --ruleCard 1 --cardsAvailable 1 7 17
tc PossibleSolutionForOdd gen_Specific_case --ruleCard 1 --cardsAvailable
tc ImpossibleSolutionForOdd gen_Specific_case --ruleCard 1 --cardsAvailable

group RuleCardEven 14

tc DPandGreedyGetsDifferentResultForEven gen_Specific_case --ruleCard 2 --cardsAvailable 9 21
tc PossibleSolutionForEven gen_Specific_case --ruleCard 2 --cardsAvailable
tc ImpossibleSolutionForEven gen_Specific_case --ruleCard 2 --cardsAvailable

group RuleCardThree 14

tc DPandGreedyGetsDifferentResultForThree gen_Specific_case --ruleCard 3 --cardsAvailable 1 2 4 8 14 15 20 21
tc PossibleSolutionForThree gen_Specific_case --ruleCard 3 --cardsAvailable
tc ImpossibleSolutionForThree gen_Specific_case --ruleCard 3 --cardsAvailable

group RuleCardFive 14

tc DPandGreedyGetsDifferentResultForFive gen_Specific_case --ruleCard 5 --cardsAvailable 6 15 20
tc PossibleSolutionForFive gen_Specific_case --ruleCard 5 --cardsAvailable
tc ImpossibleSolutionForFive gen_Specific_case --ruleCard 5 --cardsAvailable

group RuleCardSix 14

tc DPandGreedyGetsDifferentResultForSix gen_Specific_case --ruleCard 6 --cardsAvailable 1 21
tc PossibleSolutionForSix gen_Specific_case --ruleCard 6 --cardsAvailable
tc ImpossibleSolutionForSix gen_Specific_case --ruleCard 6 --cardsAvailable

group All 16






group even 20
tc 3
tc evenRulecard gen_even_rulecard --loRandom 1 --hiRandom 1000000 --loCards 1 --hiCards 21
tc EvenRulecardImpossible gen_Specific_case --ruleCard 2 --cardsAvailable 5


group odd 20
tc 1
tc 2
tc 4
tc oddRulecard gen_odd_rulecard --loRandom 1 --hiRandom 1000000 --loCards 1 --hiCards 21
tc CatchesGreedyAlgorithm gen_Specific_case --ruleCard 1 --cardsAvailable 1 5 8 17


group three 20
tc 6
tc ruleCard3Impossible gen_Specific_case --ruleCard 3 --cardsAvailable 5 10 15 20
tc ThreeRulecard gen_Specific_rulecard --loCards 1 --hiCards 21 --ruleCard 3


group five 20
tc 5
tc FiveRulecard gen_Specific_rulecard --loCards 1 --hiCards 21 --ruleCard 5
tc FiveRulecardImpossibleToHit gen_Specific_rulecard --loCards 1 --hiCards 3 --ruleCard 5
tc ruleCard5Impossible gen_Specific_case --ruleCard 5 --cardsAvailable 1 2 3 4


group all 16
tc smallNumbers1 gen_random --loRandom 1 --hiRandom 2 --loCards 1 --hiCards 5
tc smallNumbers2 gen_random --loRandom 1 --hiRandom 10 --loCards 1 --hiCards 21
tc highNumbers1 gen_random --loRandom 1 --hiRandom 5000 --loCards 1 --hiCards 21
tc highNumbers2 gen_random --loRandom 1 --hiRandom 1000000 --loCards 1 --hiCards 21




