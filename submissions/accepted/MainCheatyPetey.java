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

    private static int bottomUp(MultiSet<Integer> cardSet, int target, boolean isMostPlays,
            boolean isPlaysLimited) {
        int[] dp = new int[target + 1];
        @SuppressWarnings("unchecked")
        MultiSet<Integer>[] cardsAvailable = new MultiSet[target + 1];
        // only used if isAmountOfCardsLimited == true
        for (var i = 0; i < cardsAvailable.length && isPlaysLimited; i++) {
            cardsAvailable[i] = new MultiSet<>();
        }
        cardsAvailable[0] = cardSet.clone();
        // It takes us 0 cards to get to 0
        dp[0] = 0;
        for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
            // Initialize every current best solution to the Max/Min int,
            // which is the worst possible solution
            dp[currentTarget] = isMostPlays ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            for (int cardValue : cardSet) {
                // So we don't overdraw
                if (currentTarget - cardValue < 0 || dp[currentTarget - cardValue] == Integer.MAX_VALUE
                        || dp[currentTarget - cardValue] == Integer.MIN_VALUE
                        || isPlaysLimited && cardsAvailable[currentTarget - cardValue].numberOf(cardValue) == 0) {
                    continue;
                }
                // If we can beat current best solution, update it.
                if (isMostPlays && dp[currentTarget] < dp[currentTarget - cardValue] + 1
                        || !isMostPlays && dp[currentTarget] > dp[currentTarget - cardValue] + 1) {
                    dp[currentTarget] = dp[currentTarget - cardValue] + 1;
                    // If we are limited in amount of cards, remove the card we just used.
                    if (isPlaysLimited) {
                        cardsAvailable[currentTarget] = cardsAvailable[currentTarget - cardValue]
                                .clone();
                        cardsAvailable[currentTarget].remove(cardValue);
                    }
                }
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        MultiSet<Integer> cardSet = new MultiSet<>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            cardSet.add(scanner.nextInt(), 1);
        }

        // Only 1 of each card
        if (ruleCard == 3) {
            var result = bottomUp(cardSet, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // Only 5 of each card
        else if (ruleCard == 5) {
            for (var value : cardSet) {
                cardSet.add(value, 4);
            }
            var result = bottomUp(cardSet, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // Only 6 of each card and worst possible
        else if (ruleCard == 6) {
            for (var value : cardSet) {
                cardSet.add(value, 5);
            }
            var result = bottomUp(cardSet, target, true, true);
            System.out.println(result == Integer.MIN_VALUE ? "Impossible" : result);
        }

        // ODD: lowest amount of cards
        else if (ruleCard % 2 == 1) {

            var result = bottomUp(cardSet, target, false, false);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // EVEN: highest amount of cards
        else if (ruleCard % 2 == 0) {
            var result = bottomUp(cardSet, target, true, false);
            System.out.println(result == Integer.MIN_VALUE ? "Impossible" : result);

        }
        scanner.close();
    }
}