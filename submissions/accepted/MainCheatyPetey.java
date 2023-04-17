import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class MainCheatyPetey {
    public static void main(String[] args) {

        //Get runtime
        //long startTime = System.nanoTime();

        Scanner scanner = new Scanner(System.in);
        int firstRandomNumber = scanner.nextInt();
        int target = firstRandomNumber * 21;
        int numberOfDifferentCardValues = scanner.nextInt();

        ArrayList<Integer> valuesAvailable = new ArrayList<>();
        for (int i = 0; i < numberOfDifferentCardValues; i++) {
            valuesAvailable.add(scanner.nextInt());
        }

        //System.out.println("target: " + target);
        //System.out.println("valuesAvailable: " + valuesAvailable);

        //Dynamic programming coin change problem variant, casino themed.
        int[] dp = new int[target+1];
        //Base case is if we need to hit target 0, we can do so with 0 cards.
        dp[0] = 0;
        for (int currentTarget = 1; currentTarget <= target; currentTarget++) {
            //Initialize every current best solution to Max value, as that is clearly worse than any other solution.
            dp[currentTarget] = target+99999;
            for (int currentCardValue : valuesAvailable) {
                //If we don't overdraw.
                if (currentTarget-currentCardValue >= 0) {
                    //System.out.println("Current target:" + currentTarget + " Current card value:" + currentCardValue + " Current best solution:" + dp[currentTarget] + " New solution:" + dp[currentTarget-currentCardValue]+1);
                    //The best solution is the one with fewest amount of cards drawn.
                    //We are doing bottom-up, checking every possible solution from 1 all the way to our target.
                    //If our previous solution, plus drawing an extra card, is better than our current best solution, we update it.
                    dp[currentTarget] = min(dp[currentTarget], dp[currentTarget-currentCardValue]+1);
                }
            }
        }

        //print all DP 
        //For pushing.
        //for (int i = 0; i < dp.length; i++) {
        //    System.out.println("dp[" + i + "] = " + dp[i]);
        //}
        //Lowest card is 1, so if dp[target] is above our target, we can't reach it, as value was never changed from MAX_VALUE.
        if (abs(dp[target]) > target) {
            System.out.println("Impossible");
        }
        else {
            //System.out.println("Minimum cards:");
            System.out.println(dp[target]);
            ////Print the different cards used
            //System.out.println("Cards used:");
            //int x = target;
            //while (x > 0) {
            //    for (var c : valuesAvailable) {
            //        if (x - c >= 0 && dp[x] == dp[x - c] + 1) {
            //            System.out.println(c);
            //            x -= c;
            //            break;
            //        }
            //    }
            //}
        }
        //Print runtime in milliseconds
        //System.out.println("Runtime: " + (System.nanoTime() - startTime)/1000000 + "ms");
    }
}