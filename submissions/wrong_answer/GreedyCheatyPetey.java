import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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

    private static int greed(MultiSet<Integer> cardSet, int target, boolean isMostPlays, boolean isPlaysLimited) {
        LinkedList<Integer> orderedValues = new LinkedList<>();
        for (var value : cardSet) {
            for (var i = 0; i < cardSet.numberOf(value); i++) {
                orderedValues.add(value);
            }
        }
        Collections.sort(orderedValues);
        if (!isMostPlays) {
            Collections.reverse(orderedValues); //take biggest first
        }
        var count = 0;
        while (target > 0 && orderedValues.size() > 0) {
            var head = orderedValues.getFirst();
            if (head <= target) {
                target -= head;
                count++;
                if (isPlaysLimited) orderedValues.removeFirst();
            } 
            else if (isMostPlays) return -1; //we dont have any smaller values to use
            else orderedValues.removeFirst(); //value too big, go to next
        }
        return target == 0 ? count : -1;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();
        var cardsAvailable = new MultiSet<Integer>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            cardsAvailable.add(scanner.nextInt(), 1);
        }

        // Only 1 of each card
        if (ruleCard == 3)
        {
            var result = greed(cardsAvailable, target, false, true);
            System.out.println(result == -1 ? "Impossible" : result);
        }
        // Only 5 of each card
        else if (ruleCard == 5) {
            for (var value : cardsAvailable) {
                cardsAvailable.add(value, 4);
            }
            var result = greed(cardsAvailable, target, false, true);
            System.out.println(result == -1 ? "Impossible" : result);

        }
        //max 6 of each and worst
        else if (ruleCard == 6) {
            for (var value : cardsAvailable) {
                cardsAvailable.add(value, 5);
            }
            var result = greed(cardsAvailable, target, true, true);
            System.out.println(result == -1 ? "Impossible" : result);
        }
        // ODD: lowest amount of cards
        else if (ruleCard % 2 == 1) {
            var result = greed(cardsAvailable, target, false, false);
            System.out.println(result == -1 ? "Impossible" : result);
        }
        //Take as many as possible
        else if (ruleCard % 2 == 0) {
            var result = greed(cardsAvailable, target, true, false);
            System.out.println(result == -1 ? "Impossible" : result);

        }
        scanner.close();
    }
}