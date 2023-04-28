import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class BruteForceCheatyPetey {
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
            var list = new ArrayList<T>();
            for (T obj : ms.keySet()) {
                for (int i = numberOf(obj); i > 0; i--) {
                    list.add(obj);
                }
            }
            return list.iterator();
        }

        @Override
        @SuppressWarnings("unchecked")
        public MultiSet<T> clone() {
            return new MultiSet<T>((HashMap<T, Integer>) ms.clone());
        }
    }

    public static ArrayList<Integer[]> generatePermutations(ArrayList<Integer> coins) {
        ArrayList<Integer[]> permutations = new ArrayList<>();
        for (int length = 1; length <= coins.size(); length++) {
            permute(permutations, coins, new Integer[length], 0, length);
        }
        return permutations;
    }

    public static void permute(ArrayList<Integer[]> permutations, ArrayList<Integer> coins, Integer[] permutation,
            int start, int length) {
        if (length == 0) {
            permutations.add(permutation.clone());
        } else {
            for (int i = start; i <= coins.size() - length; i++) {
                permutation[permutation.length - length] = coins.get(i);
                permute(permutations, coins, permutation, i + 1, length - 1);
            }
        }
    }

    public static void swap(ArrayList<Integer> coins, int i, int j) {
        int temp = coins.get(i);
        coins.set(i, coins.get(j));
        coins.set(j, temp);
    }

    public static void main(String[] args) {

        // Get runtime
        // long startTime = System.nanoTime();

        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        ArrayList<Integer> valuesAvailable = new ArrayList<>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            valuesAvailable.add(scanner.nextInt());
        }

        if (ruleCard % 2 == 0) {

        } else if (ruleCard == 5)
        // Only 5 of each card
        {

        } else if (ruleCard == 3) {
            // Only 1 of each card

            ArrayList<Integer[]> permutations = generatePermutations(valuesAvailable);
            ArrayList<Integer[]> correctPermutations = new ArrayList<>();
            int minimumCardsUsed = Integer.MAX_VALUE;

            // Example for 3 cards: 1, 2, 3
            // Lenght 1: 1, 2, 3
            // length 2: 12; 13; 23
            // length 3: 123
            for (int length = 1; length <= valuesAvailable.size(); length++) {
                for (Integer[] permutation : permutations) {
                    // Gets all permutations.
                    if (permutation.length == length) {
                        int sum = 0;
                        for (int i = 0; i < permutation.length; i++) {
                            sum += permutation[i];
                        }
                        if (sum == target) {
                            // If permutation is correct, we add it and check if it is a lower amount of
                            // cards.
                            // We don't neccesarliy have to add it, for our solution we just print minCards
                            correctPermutations.add(permutation);
                            if (permutation.length < minimumCardsUsed) {
                                minimumCardsUsed = permutation.length;
                            }
                        }
                    }
                }
            }

            if (correctPermutations.size() > 0) {
                System.out.println("Correct permutations:");
                for (Integer[] permutation : correctPermutations) {
                    if (permutation.length == minimumCardsUsed) {
                        System.out.println(Arrays.toString(permutation));
                    }
                }
                System.out.println("Minimum coins used: " + minimumCardsUsed);
            } else {
                System.out.println("No combination adds up to 21.");
            }
        }

        else if (ruleCard % 2 == 1) {
            // ODD: lowest amount of cards

        }

    }
}
