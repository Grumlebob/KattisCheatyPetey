import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class MainCheatyPetey {
    private static class MultiSet<T> implements Iterable<T>, Cloneable {

        private HashMap<T, Integer> ms;

        public MultiSet() {
            ms = new HashMap<>();
        }

        private MultiSet(HashMap<T, Integer> init) {
            ms = init;
        }

        public void add(T obj, int amount) {
            var count = numberOf(obj);
            ms.put(obj, count + amount);
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
            return ms.keySet().iterator();
        }

        @Override
        @SuppressWarnings("unchecked")
        public MultiSet<T> clone() {
            return new MultiSet<T>((HashMap<T, Integer>) ms.clone());
        }
    }
    
    private static int bottumUp(MultiSet<Integer> availableValues, int target, boolean worst, boolean oneOfEach) {
        int[] dp = new int[target + 1];
        @SuppressWarnings("unchecked")
        MultiSet<Integer>[] valuesAvailableFrom = new MultiSet[target + 1];; //only used if oneOfEach == true
        for (var i = 0; i < valuesAvailableFrom.length && oneOfEach; i++) {
            valuesAvailableFrom[i] = availableValues.clone();
        }
        dp[0] = 0;
        for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
            // Initialize every current best solution to Max value, as that is worse than
            // any other solution.
            // When finding minimum we set this number high, if we are finding max we set it
            // low.
            // Because we want to find something where they draw the most cards, and drawing
            // 1 card is more than 0.
            dp[currentTarget] = worst ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            for (int currentCardValue : availableValues) {
                // If we don't overdraw.
                if (currentTarget - currentCardValue < 0 || dp[currentTarget - currentCardValue] == Integer.MAX_VALUE || dp[currentTarget - currentCardValue] == Integer.MIN_VALUE) {
                    continue;
                }
                // System.out.println("Current target:" + currentTarget + " Current card value:"
                // + currentCardValue
                // + " Current best solution:" + dp[currentTarget] + " New solution:"
                // + dp[currentTarget - currentCardValue] + 1);
                if (worst && dp[currentTarget] < dp[currentTarget - currentCardValue] + 1 || !worst && dp[currentTarget] > dp[currentTarget - currentCardValue] + 1) {
                    dp[currentTarget] = dp[currentTarget - currentCardValue] + 1;
                    if (oneOfEach) {
                        valuesAvailableFrom[currentTarget] = valuesAvailableFrom[currentTarget - currentCardValue].clone();
                        valuesAvailableFrom[currentTarget].remove(currentCardValue);
                    }
                }
                // System.out.println(dp[currentTarget]);
            }
        }

        return dp[target];
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
            valuesAvailable.add(scanner.nextInt(), 1);
        }

        // System.out.println("target: " + target);
        // System.out.println("valuesAvailable: " + valuesAvailable);

        // Dynamic programming coin change problem variant, casino themed.
        
        // Only 1 of each card
        if (ruleCard == 3) {
            var result = bottumUp(valuesAvailable, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ?  "Impossible" : result);
        }

        // Only 5 of each card 
        else if (ruleCard == 5) {
            for (var value : valuesAvailable) {
                valuesAvailable.add(value, 4);
            }
            var result = bottumUp(valuesAvailable, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ?  "Impossible" : result);
        }

        // Only 6 of each card and worst possible
        else if (ruleCard == 6) {
            for (var value : valuesAvailable) {
                valuesAvailable.add(value, 5);
            }
            var result = bottumUp(valuesAvailable, target, true, true);
            System.out.println(result == Integer.MIN_VALUE ?  "Impossible" : result);
        }

        // ODD: lowest amount of cards
        else if (ruleCard % 2 == 1) {
            
            var result = bottumUp(valuesAvailable, target, false, false);
            System.out.println(result == Integer.MAX_VALUE ?  "Impossible" : result);
        }

        // EVEN: highest amount of cards
        else if (ruleCard % 2 == 0) {
            var result = bottumUp(valuesAvailable, target, true, false);
            System.out.println(result == Integer.MIN_VALUE ?  "Impossible" : result);

        } 
        scanner.close();
    }
}