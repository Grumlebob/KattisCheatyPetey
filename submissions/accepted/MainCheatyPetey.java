import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class MainCheatyPetey {
    private static class MultiSet<T> implements Iterable<T>{
        
        private HashMap<T, Integer> ms;

        public MultiSet() {
            ms = new HashMap<>();
        }

        public void add(T obj, int amount) {
            var count = numberOf(obj);
            ms.put(obj, count + amount);
        }

        public void add(T obj) {
            add(obj, 1);
        }

        public void remove(T obj, int amount) {
            var count = numberOf(obj);
            if (count <= amount) {
                ms.remove(obj);
            } else {
                ms.put(obj, count - amount);
            }
        }

        public void remove(T obj) {
            remove(obj, 1);
        }

        public int numberOf(T obj) {
            var count = ms.get(obj);
            return count == null ? 0 : count;
        }

        @Override
        public Iterator<T> iterator() {
            var list = new ArrayList<T>();
            for (T obj : ms.keySet()) {
                for (int i = numberOf(obj); i > 0; i--) {
                    list.add(obj);
                }
            }
            return list.iterator();
        }
    }
    public static void main(String[] args) {

        // Get runtime
        // long startTime = System.nanoTime();

        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        MultiSet<Integer> valuesAvailable = new MultiSet<>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            valuesAvailable.add(scanner.nextInt());
        }

        // System.out.println("target: " + target);
        // System.out.println("valuesAvailable: " + valuesAvailable);

        // Dynamic programming coin change problem variant, casino themed.
        int[] dp = new int[target + 1];

        if (ruleCard % 2 == 0) {
            // EVEN: highest amount of cards
            dp[0] = 0;
            for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
                // Initialize every current best solution to Max value, as that is worse than
                // any other solution.
                // When finding minimum we set this number high, if we are finding max we set it
                // low.
                // Because we want to find something where they draw the most cards, and drawing
                // 1 card is more than 0.
                dp[currentTarget] = 0;
                for (int currentCardValue : valuesAvailable) {
                    // If we don't overdraw.
                    if (currentTarget - currentCardValue >= 0 && currentTarget + currentCardValue <= target + 1) {
                        // System.out.println("Current target:" + currentTarget + " Current card value:"
                        // + currentCardValue
                        // + " Current best solution:" + dp[currentTarget] + " New solution:"
                        // + dp[currentTarget - currentCardValue] + 1);
                        dp[currentTarget] = Math.max(dp[currentTarget], dp[currentTarget - currentCardValue] + 1);
                        // System.out.println(dp[currentTarget]);
                    }
                }
            }
            // Lowest card is 1, so if dp[target] is above our target, we can't reach it, as
            // value was never changed from MAX_VALUE.
            if (abs(dp[target]) == 0) {
                System.out.println("Impossible");
            } else {
                System.out.println(dp[target]);
                // Print the different cards used
                // System.out.println("Cards used:");
                // int x = target;
                // while (x > 0) {
                // for (var c : valuesAvailable) {
                // if (x - c >= 0 && dp[x] == dp[x - c] + 1) {
                // System.out.println(c);
                // x -= c;
                // break;
                // }
                // }
                // }
            }

        } else if (ruleCard == 5)
        // Only 5 of each card
        {
            @SuppressWarnings("unchecked") //java doesn't like arrays of generics
            MultiSet<Integer>[] valuesAvailableFrom = new MultiSet[dp.length];
            for (int i = 0; i < valuesAvailableFrom.length; i++) {
                var aux = new MultiSet<Integer>();
                for (var val : valuesAvailable) {
                    aux.add(val, 5);
                }
                valuesAvailableFrom[i] = aux; //for target i you have x cards to pick from
            }
            dp[0] = 0;
            for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
                // Initialize every current best solution to Max value, as that is clearly worse
                // than any other solution.
                dp[currentTarget] = target + 99999;
                for (int currentCardValue : valuesAvailable) {
                    // If we don't overdraw.
                    if (currentTarget - currentCardValue >= 0) {
                        // System.out.println("Current target:" + currentTarget + " Current card value:"
                        // + currentCardValue + " Current best solution:" + dp[currentTarget] + " New
                        // solution:" + dp[currentTarget-currentCardValue]+1);
                        // The best solution is the one with fewest amount of cards drawn.
                        // We are doing bottom-up, checking every possible solution from 1 all the way
                        // to our target.
                        // If our previous solution, plus drawing an extra card, is better than our
                        // current best solution, we update it.
                        if (dp[currentTarget - currentCardValue] + 1 < dp[currentTarget] && valuesAvailableFrom[currentTarget - currentCardValue].numberOf(currentCardValue) > 0) {
                            valuesAvailableFrom[currentTarget].remove(currentCardValue);
                            dp[currentTarget] = dp[currentTarget - currentCardValue] + 1;
                        } 
                        else {
                            dp[currentTarget] = dp[currentTarget];
                        }
                    }
                }
            }
            // Lowest card is 1, so if dp[target] is above our target, we can't reach it, as
            // value was never changed from MAX_VALUE.
            if (abs(dp[target]) > target) {
                System.out.println("Impossible");
            } else {
                // System.out.println("Minimum cards:");
                System.out.println(dp[target]);
                //// Print the different cards used
                // System.out.println("Cards used:");
                // int x = target;
                // while (x > 0) {
                // for (var c : valuesAvailable) {
                // if (x - c >= 0 && dp[x] == dp[x - c] + 1) {
                // System.out.println(c);
                // x -= c;
                // break;
                // }
                // }
                // }
            }
        } 
        else if (ruleCard == 3) 
        // Only 1 of each card
        {
            @SuppressWarnings("unchecked") //java doesn't like arrays of generics
            MultiSet<Integer>[] valuesAvailableFrom = new MultiSet[dp.length];
            for (int i = 0; i < valuesAvailableFrom.length; i++) {
                var aux = new MultiSet<Integer>();
                for (var val : valuesAvailable) {
                    aux.add(val);
                }
                valuesAvailableFrom[i] = aux; //for target i you have x cards to pick from
            }
            dp[0] = 0;
            for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
                // Initialize every current best solution to Max value, as that is clearly worse
                // than any other solution.
                dp[currentTarget] = target + 99999;
                for (int currentCardValue : valuesAvailable) {
                    // If we don't overdraw.
                    if (currentTarget - currentCardValue >= 0) {
                        // System.out.println("Current target:" + currentTarget + " Current card value:"
                        // + currentCardValue + " Current best solution:" + dp[currentTarget] + " New
                        // solution:" + dp[currentTarget-currentCardValue]+1);
                        // The best solution is the one with fewest amount of cards drawn.
                        // We are doing bottom-up, checking every possible solution from 1 all the way
                        // to our target.
                        // If our previous solution, plus drawing an extra card, is better than our
                        // current best solution, we update it.
                        if (dp[currentTarget - currentCardValue] + 1 < dp[currentTarget] && valuesAvailableFrom[currentTarget - currentCardValue].numberOf(currentCardValue) > 0) {
                            valuesAvailableFrom[currentTarget].remove(currentCardValue);
                            dp[currentTarget] = dp[currentTarget - currentCardValue] + 1;
                        } 
                        else {
                            dp[currentTarget] = dp[currentTarget];
                        }
                    }
                }
            }
            // Lowest card is 1, so if dp[target] is above our target, we can't reach it, as
            // value was never changed from MAX_VALUE.
            if (abs(dp[target]) > target) {
                System.out.println("Impossible");
            } else {
                // System.out.println("Minimum cards:");
                System.out.println(dp[target]);
                //// Print the different cards used
                // System.out.println("Cards used:");
                // int x = target;
                // while (x > 0) {
                // for (var c : valuesAvailable) {
                // if (x - c >= 0 && dp[x] == dp[x - c] + 1) {
                // System.out.println(c);
                // x -= c;
                // break;
                // }
                // }
                // }
            }
        }

        else if (ruleCard % 2 == 1) {
            // ODD: lowest amount of cards
            dp[0] = 0;
            for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
                // Initialize every current best solution to Max value, as that is clearly worse
                // than any other solution.
                dp[currentTarget] = target + 99999;
                for (int currentCardValue : valuesAvailable) {
                    // If we don't overdraw.
                    if (currentTarget - currentCardValue >= 0) {
                        // System.out.println("Current target:" + currentTarget + " Current card value:"
                        // + currentCardValue + " Current best solution:" + dp[currentTarget] + " New
                        // solution:" + dp[currentTarget-currentCardValue]+1);
                        // The best solution is the one with fewest amount of cards drawn.
                        // We are doing bottom-up, checking every possible solution from 1 all the way
                        // to our target.
                        // If our previous solution, plus drawing an extra card, is better than our
                        // current best solution, we update it.
                        dp[currentTarget] = min(dp[currentTarget], dp[currentTarget - currentCardValue] + 1);
                    }
                }
            }

            // Lowest card is 1, so if dp[target] is above our target, we can't reach it, as
            // value was never changed from MAX_VALUE.
            if (abs(dp[target]) > target) {
                System.out.println("Impossible");
            } else {
                // System.out.println("Minimum cards:");
                System.out.println(dp[target]);
                //// Print the different cards used
                // System.out.println("Cards used:");
                // int x = target;
                // while (x > 0) {
                // for (var c : valuesAvailable) {
                // if (x - c >= 0 && dp[x] == dp[x - c] + 1) {
                // System.out.println(c);
                // x -= c;
                // break;
                // }
                // }
                // }
            }

        }
        // Base case is if we need to hit target 0, we can do so with 0 cards.

        // print all DP
        // For pushing.
        // for (int i = 0; i < dp.length; i++) {
        // System.out.println("dp[" + i + "] = " + dp[i]);
        // }

        // Print runtime in milliseconds
        // System.out.println("Runtime: " + (System.nanoTime() - startTime)/1000000 +
        // "ms");
    }
}