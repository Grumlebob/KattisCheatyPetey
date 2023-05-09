#!/bin/bash

# USE_SCORING=100 #Pass-fail problem.
# run this as ./generate.sh from the data directory. It populates data/secret

. ../../_testdata_tools/gen.sh

use_solution MainCheatyPetey.java # Use submissions/accepted/

# all the generators are introduced here
compile gen_random.py
compile gen_Specific_case.py
compile gen_even_rulecard.py
compile gen_odd_rulecard.py

# TODO RET DET HER. LAV DEFAULT TESTS DER GEMMER GREEDY FORKERT LØSNINGER. there are six *.in files in data/sample
samplegroup
sample 1
sample 2
sample 3
sample 4
sample 5

# generate tests here
group RuleCardOne 14

tc DPandGreedyGetsDifferentResultForOne gen_Specific_case --ruleCard 1 --cardsAvailable 1 5 8 17
tc PossibleSolutionForOne gen_Specific_case --ruleCard 1 --cardsAvailable 1 3 5 7 9 11 13 15 17 19 21
tc ImpossibleSolutionForOne gen_Specific_case --ruleCard 1 --cardsAvailable 6 8
tc RandomLowCardsForOne gen_random --loRuleCard 1 --hiRuleCard 1 --loCards 1 --hiCards 10
tc RandomHighCardsForOne gen_random --loRuleCard 1 --hiRuleCard 1 --loCards 10 --hiCards 21
tc RandomAllCardsForOne gen_random --loRuleCard 1 --hiRuleCard 1 --loCards 1 --hiCards 21
tc 1
tc 2

group RuleCardOdd 14

tc DPandGreedyGetsDifferentResultForOdd gen_Specific_case --ruleCard 1 --cardsAvailable 1 7 17
tc PossibleSolutionForOdd gen_Specific_case --ruleCard 1 --cardsAvailable 2 4 6 8 10 12 14 16 18 20
tc ImpossibleSolutionForOdd gen_Specific_case --ruleCard 1 --cardsAvailable 10 14
tc RandomLowInputForOdd gen_odd_rulecard --loRuleCard 6 --hiRuleCard 10 --loCards 1 --hiCards 21
tc RandomMiddleInputForOdd gen_odd_rulecard --loRuleCard 6 --hiRuleCard 10000 --loCards 1 --hiCards 21
tc RandomHighInputForOdd gen_odd_rulecard --loRuleCard 6 --hiRuleCard 100000 --loCards 1 --hiCards 21

group RuleCardEven 14

tc DPandGreedyGetsDifferentResultForEven gen_Specific_case --ruleCard 2 --cardsAvailable 9 21
tc PossibleSolutionForEven gen_Specific_case --ruleCard 2 --cardsAvailable 1 5 11 15 19
tc ImpossibleSolutionForEven gen_Specific_case --ruleCard 2 --cardsAvailable 9 20
tc RandomLowInputForEven gen_even_rulecard --loRuleCard 7 --hiRuleCard 20 --loCards 1 --hiCards 21
tc RandomMiddleInputForEven gen_even_rulecard --loRuleCard 7 --hiRuleCard 10000 --loCards 1 --hiCards 21
tc RandomHighInputForEven gen_even_rulecard --loRuleCard 7 --hiRuleCard 100000 --loCards 1 --hiCards 21
tc 3

group RuleCardThree 14

tc DPandGreedyGetsDifferentResultForThree gen_Specific_case --ruleCard 3 --cardsAvailable 1 2 4 8 14 15 20 21
tc PossibleSolutionForThree gen_Specific_case --ruleCard 3 --cardsAvailable 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21
tc ImpossibleSolutionForThree gen_Specific_case --ruleCard 3 --cardsAvailable 1 2 3 4 5
tc RandomLowCardsForThree gen_random --loRuleCard 3 --hiRuleCard 3 --loCards 1 --hiCards 10
tc RandomHighCardsForThree gen_random --loRuleCard 3 --hiRuleCard 3 --loCards 10 --hiCards 21
tc RandomAllCardsForThree gen_random --loRuleCard 3 --hiRuleCard 3 --loCards 1 --hiCards 21
tc 4

group RuleCardFive 14

tc DPandGreedyGetsDifferentResultForFive gen_Specific_case --ruleCard 5 --cardsAvailable 6 15 20
tc PossibleSolutionForFive gen_Specific_case --ruleCard 5 --cardsAvailable 1 5 7 10 14 17 21
tc ImpossibleSolutionForFive gen_Specific_case --ruleCard 5 --cardsAvailable 1 2 3 4 5
tc RandomLowCardsForFive gen_random --loRuleCard 5 --hiRuleCard 5 --loCards 1 --hiCards 10
tc RandomHighCardsForFive gen_random --loRuleCard 5 --hiRuleCard 5 --loCards 10 --hiCards 21
tc RandomAllCardsForFive gen_random --loRuleCard 5 --hiRuleCard 5 --loCards 1 --hiCards 21
tc 5

group RuleCardSix 14

tc DPandGreedyGetsDifferentResultForSix gen_Specific_case --ruleCard 6 --cardsAvailable 1 21
tc PossibleSolutionForSix gen_Specific_case --ruleCard 6 --cardsAvailable 2 4 6 8 10 12 14 16 18 20
tc ImpossibleSolutionForSix gen_Specific_case --ruleCard 6 --cardsAvailable 1 2 3 4 5
tc RandomLowCardsForSix gen_random --loRuleCard 6 --hiRuleCard 6 --loCards 1 --hiCards 10
tc RandomHighCardsForSix gen_random --loRuleCard 6 --hiRuleCard 6 --loCards 10 --hiCards 21
tc RandomAllCardsForSix gen_random --loRuleCard 6 --hiRuleCard 6 --loCards 1 --hiCards 21

group All 16

tc HighestLimitLowCards gen_random --loRuleCard 100000 --hiRuleCard 100000 --loCards 1 --hiCards 10
tc HighestLimitHighCards gen_random --loRuleCard 100000 --hiRuleCard 100000 --loCards 10 --hiCards 21
tc HighestLimitAllCards gen_random --loRuleCard 100000 --hiRuleCard 100000 --loCards 1 --hiCards 21
tc CanBeAnything gen_random --loRuleCard 1 --hiRuleCard 100000 --loCards 1 --hiCards 21
tc DPandGreedyGetsDifferentResultForAll gen_Specific_case --ruleCard 1 --cardsAvailable 1 7 14 18 
