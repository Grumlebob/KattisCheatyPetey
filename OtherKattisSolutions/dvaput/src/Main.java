import java.util.Scanner;
import java.util.Arrays;

public class Main {

    static int[] sa; // final suffix array
    static final int ALPHABET = 27; // BASECHAR + a-z
    static final char BASECHAR = ('a' - 1); // whatever comes before 'a' (helps sort "a" before "aa" and identifies
                                            // cycles)

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        scanner.nextLine();
        var inputString = scanner.nextLine();
        sa = suffixArrayConstruction(inputString);
        lcp = constructLCPArray(inputString, sa);
        System.out.println(maxLCP);
    }

    //100 % his solution
    //https://github.com/stevenhalim/cpbook-code/blob/master/ch6/sa_lcp.py
    //Sorting with cyclic shifts
    public static int[] suffixArrayConstruction(String s) {
        return Arrays.copyOfRange(sortCyclicShifts(s + '\0'), 1, s.length() + 1);
    }
    public static int[] sortCyclicShifts(String s) {
        int n = s.length();
        int[] suffixArray = new int[n];
        int[] equivalenceClasses = new int[n];
        int[] charCount = new int[Math.max(ALPHABET + 1, n + 1)];

        // Count number of occurrences for each character in input string
        for (int i = 0; i < n; i++) {
            charCount[s.charAt(i) - BASECHAR + 1]++;
        }
        // Calculate the starting index for each character in the sorted suffix array
        for (int i = 1; i < alphabet; i++) {
            charCount[i] += charCount[i-1];
        }
        // Construct the sorted suffix array
        for (int i = n-1; i >= 0; i--) {
            charCount[s.charAt(i)]--;
            suffixArray[charCount[s.charAt(i)]] = i;
        }

        // Divide suffixes into equivalence classes depending on their first letter
        int classN = 0;
        equivalenceClasses[suffixArray[0]] = classN; // first element will always be in the first class
        for (int i = 1; i < n; i++) {
            if (s.charAt(suffixArray[i]) != s.charAt(suffixArray[i-1])) {
                classes++;
            }
            equivalenceClasses[suffixArray[i]] = classN;
        }

        // keep sorting after classes. Double suffix size every time
        int[] tempSufixArray = new int[n];
        int[] tempEquivalanceClasses = new int[n];
        for (int shift = 1; shift < n; shift *= 2) {
            // shift left.
            for (int i = 0; i < n; i++) {
                tempSufixArray[i] = (suffixArray[i] - shift + n) % n; // (... + n) % n makes it cycle from -k to n-k instead of going out of bounds
            }
            Arrays.fill(charCount, 0);
            for (int i = 0; i < n; i++) {
                charCount[equivalenceClasses[tempSufixArray[i]] + 1]++;
            }
            for (int i = 1; i < classes; i++) {
                charCount[i] += charCount[i-1];
            }

            // Construct the sorted suffix array based on the current equivalence classes
            for (int i = n-1; i >= 0; i--) {
                charCount[equivalenceClasses[tempSufixArray[i]]]--;
                suffixArray[charCount[equivalenceClasses[tempSufixArray[i]]]] = tempSufixArray[i];
                charCount[equivalenceClasses[tempSufixArray[i]]]++;
            }
            // Assign new equivalence classes based on the sorted suffix array
            classN = 0;
            tempEquivalanceClasses[suffixArray[0]] = classN; // first element, first class
            for (int i = 1; i < n; i++) {
                int cur = equivalenceClasses[suffixArray[i]] * alphabet + equivalenceClasses[(suffixArray[i] + (1 << h)) % n];
                int prev = equivalenceClasses[suffixArray[i-1]] * alphabet + equivalenceClasses[(suffixArray[i-1] + (1 << h)) % n];
                if (cur != prev) {
                    classN++;
                }
                tempEquivalanceClasses[suffixArray[i]] = classN;
            }
            // Swap equivalence class arrays to keep the old values for next round
            var temp = equivalenceClasses;
            equivalenceClasses = tempEquivalanceClasses;
            tempEquivalanceClasses = temp;
        }
        return Arrays.copyOfRange(suffixArray, 1, suffixArray.length);
    }


    //Runtime: O(N) - N : Length of string
    public static int[] constructLCPArray(String s, int[] sa) {
        int n = s.length();
        int[] lcp = new int[n - 1];
        int[] invSa = new int[n];
        maxLCP = 0; // initialize maxLCPFound to 0
        for (int i = 0; i < n; i++) {
            invSa[sa[i]] = i;
        }
        int commonChars = 0;
        // Start from the longest suffix x. Compare it to the element that comes before
        // x in sa
        for (int i = 0; i < sa.length; i++) {
            if (saIndexOf[i] == 0) {
                commonChars = 0;
                continue;
            }
            int prev = sa[saIndexOf[i] - 1];
            boolean withinBounds = i + commonChars < s.length() && prev + commonChars < s.length();
            while (withinBounds && s.charAt(i + commonChars) == s.charAt(prev + commonChars)) {
                commonChars++;
                withinBounds = i + commonChars < s.length() && prev + commonChars < s.length();
            }
            maxLCP = Math.max(maxLCP, commonChars);
            if (commonChars > 0) commonChars--; // next suffix is one char shorter and has at least as many common chars - 1
        }
        return lcp;
    }


    //Finds that actual string, that is the longest repeating substring
    public static String findLongestRepeatingSubstring(String s) {
        int n = s.length();
        int maxLcp = 0;
        int maxLcpIndex = -1;
        for (int i = 0; i < n - 1; i++) {
            if ((sa[i] < n / 2 && sa[i + 1] >= n / 2) || (sa[i + 1] < n / 2 && sa[i] >= n / 2)) {
                if (lcp[i] > maxLcp) {
                    maxLcp = lcp[i];
                    maxLcpIndex = i;
                }
            }
        }
        if (maxLcp > 0) {
            int startPos = sa[maxLcpIndex];
            return s.substring(startPos, startPos + maxLcp);
        } else {
            return "";
        }
    }
}