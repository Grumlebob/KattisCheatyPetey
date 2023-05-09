import java.util.ArrayList;
import java.util.Scanner;

public class WithoutMemoization {

    private static int recursive(ArrayList<Integer> cardSet, int target, boolean isMostPlays) {
        if (target == 0) {
            return 0;
        }

        int best = isMostPlays ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int cardValue : cardSet) {
            if (target - cardValue >= 0) {
                int subResult = recursive(cardSet, target - cardValue, isMostPlays);
                if (subResult != Integer.MAX_VALUE && subResult != Integer.MIN_VALUE) {
                    if (isMostPlays) {
                        best = Math.max(best, subResult + 1);
                    } else {
                        best = Math.min(best, subResult + 1);
                    }
                }
            }
        }
        return best;
    }

    private static int calculate(ArrayList<Integer> cardSet, int target, boolean isMostPlays) {
        int result = recursive(cardSet, target, isMostPlays);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ruleCard = scanner.nextInt();
        int target = ruleCard * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        ArrayList<Integer> cardSet = new ArrayList<>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            cardSet.add(scanner.nextInt());
        }


        // ODD: lowest amount of cards
        if (ruleCard % 2 == 1) {
            var result = calculate(cardSet, target, false);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        }

        // EVEN: highest amount of cards
        else if (ruleCard % 2 == 0) {
            var result = calculate(cardSet, target, true);
            System.out.println(result == Integer.MIN_VALUE ? "Impossible" : result);

        }
        scanner.close();
    }
}
