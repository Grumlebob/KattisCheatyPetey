import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class WithoutMemorization {
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

    private static int recursive(MultiSet<Integer> cardSet, int target, boolean isMostPlays,
            boolean isPlaysLimited) {
        if (target == 0) {
            return 0;
        }
        if (target == 3) {
            System.out.println();
            System.out.println();
        }

        int best = isMostPlays ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int cardValue : cardSet) {
            if (target - cardValue >= 0) {
                MultiSet<Integer> newCardSet = cardSet.clone();
                if (isPlaysLimited) newCardSet.remove(cardValue);
                int subResult = recursive(newCardSet, target - cardValue, isMostPlays, isPlaysLimited);
                if (subResult != Integer.MAX_VALUE && subResult != Integer.MIN_VALUE) {
                    if (isMostPlays) {
                        best = Math.max(best, subResult + 1);
                    } else {
                        best = Math.min(best, subResult + 1);
                    }
                }
            }
        }

        if (isPlaysLimited) {
            int remaining = cardSet.numberOf(target);
            if (remaining > 0 && best != Integer.MAX_VALUE && best != Integer.MIN_VALUE) {
                return best;
            } else {
                return Integer.MAX_VALUE;
            }
        } else {
            return best;
        }
    }

    private static int calculate(MultiSet<Integer> cardSet, int target, boolean isMostPlays,
            boolean isPlaysLimited) {
        int result = recursive(cardSet, target, isMostPlays, isPlaysLimited);
        return result;
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
            var result = calculate(cardSet, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // Only 5 of each card
        else if (ruleCard == 5) {
            for (var value : cardSet) {
                cardSet.add(value, 4);
            }
            var result = calculate(cardSet, target, false, true);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // Only 6 of each card and worst possible
        else if (ruleCard == 6) {
            for (var value : cardSet) {
                cardSet.add(value, 5);
            }
            var result = calculate(cardSet, target, true, true);
            System.out.println(result == Integer.MIN_VALUE ? "Impossible" : result);
        }

        // ODD: lowest amount of cards
        else if (ruleCard % 2 == 1) {
            var result = calculate(cardSet, target, false, false);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // EVEN: highest amount of cards
        else if (ruleCard % 2 == 0) {
            var result = calculate(cardSet, target, true, false);
            System.out.println(result == Integer.MIN_VALUE ? "Impossible" : result);

        }
        scanner.close();
    }
}
