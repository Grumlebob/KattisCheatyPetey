import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class GreedyCheatyPetey {
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

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        if (ruleCard % 2 == 0) {
            // EVEN: highest amount of cards

        } else if (ruleCard == 5)
        // Only 5 of each card
        {
            ArrayList<Integer> valuesAvailable = new ArrayList<>();
            for (int i = 0; i < numberOfDifferentCardValues; i++) {
                valuesAvailable.add(scanner.nextInt());
            }
            // Sorted by highest value first
            valuesAvailable.sort((a, b) -> b - a);

            @SuppressWarnings("unchecked") // java doesn't like arrays of generics
            MultiSet<Integer>[] valuesAvailableFrom = new MultiSet[target + 1];
            for (int i = 0; i < valuesAvailableFrom.length; i++) {
                var aux = new MultiSet<Integer>();
                for (var val : valuesAvailable) {
                    aux.add(val, 5);
                }
                valuesAvailableFrom[i] = aux; // for target i you have x cards to pick from
            }

            int currentTarget = target;
            int cardsUsedCounter = 0;

            for (int value : valuesAvailable) {
                while (currentTarget - value >= 0 && valuesAvailableFrom[value].numberOf(value) > 0) {
                    valuesAvailableFrom[value].remove(value);
                    currentTarget -= value;
                    cardsUsedCounter++;
                    if (currentTarget == 0) {
                        break;
                    }
                }
            }

            // Print all the cards used
            // for (int i = 1; i <= 7; i++) {
            // System.out.println("Target " + i + ": " +
            // valuesAvailableFrom[i].numberOf(i));
            // }

            if (currentTarget != 0) {
                System.out.println("Impossible");
            } else {
                System.out.println(cardsUsedCounter);
            }
        } else if (ruleCard == 3)
        // Only 1 of each card
        {
            ArrayList<Integer> valuesAvailable = new ArrayList<>();
            for (int i = 0; i < numberOfDifferentCardValues; i++) {
                valuesAvailable.add(scanner.nextInt());
            }
            // Sorted by highest value first
            valuesAvailable.sort((a, b) -> b - a);

            @SuppressWarnings("unchecked") // java doesn't like arrays of generics
            MultiSet<Integer>[] valuesAvailableFrom = new MultiSet[target + 1];
            for (int i = 0; i < valuesAvailableFrom.length; i++) {
                var aux = new MultiSet<Integer>();
                for (var val : valuesAvailable) {
                    aux.add(val, 1);
                }
                valuesAvailableFrom[i] = aux; // for target i you have x cards to pick from
            }

            int currentTarget = target;
            int cardsUsedCounter = 0;

            for (int value : valuesAvailable) {
                while (currentTarget - value >= 0 && valuesAvailableFrom[value].numberOf(value) > 0) {
                    valuesAvailableFrom[value].remove(value);
                    currentTarget -= value;
                    cardsUsedCounter++;
                    if (currentTarget == 0) {
                        break;
                    }
                }
            }

            // Print all the cards used
            // for (int i = 1; i <= 7; i++) {
            // System.out.println("Target " + i + ": " +
            // valuesAvailableFrom[i].numberOf(i));
            // }

            if (currentTarget != 0) {
                System.out.println("Impossible");
            } else {
                System.out.println(cardsUsedCounter);
            }
        }

        else if (ruleCard % 2 == 1) {
            // ODD: lowest amount of cards
            ArrayList<Integer> valuesAvailable = new ArrayList<>();
            for (int i = 0; i < numberOfDifferentCardValues; i++) {
                valuesAvailable.add(scanner.nextInt());
            }
            // Sorted by highest value first
            valuesAvailable.sort((a, b) -> b - a);

            int currentTarget = target;
            int cardsUsedCounter = 0;
            for (int value : valuesAvailable) {
                while (currentTarget - value >= 0) {
                    currentTarget -= value;
                    cardsUsedCounter++;
                    if (currentTarget == 0) {
                        break;
                    }
                }
            }

            if (currentTarget != 0) {
                System.out.println("Impossible");
            } else {
                System.out.println(cardsUsedCounter);
            }

        }
    }
}