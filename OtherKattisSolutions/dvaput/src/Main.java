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
        sa = SuffixArrayCyclicShifts(inputString);
        System.out.println(findMaxLCP(inputString, sa));
        scanner.close();
    }

    // Runtime: O(N lgN) with N = string length
    public static int[] SuffixArrayCyclicShifts(String s) {
        s = s + BASECHAR;
        int n = s.length();
        int[] suffixArray = new int[n];
        int[] equivalenceClasses = new int[n];
        int[] charCount = new int[Math.max(ALPHABET + 1, n + 1)];

        // Count number of occurrences for each character in input string
        for (int i = 0; i < n; i++) {
            charCount[s.charAt(i) - BASECHAR + 1]++;
        }
        // Calculate starting index for each character in the sorted suffix array
        for (int i = 1; i < ALPHABET; i++) {
            charCount[i] += charCount[i - 1];
        }
        // Construct the sorted suffix array
        for (int i = 0; i < n; i++) {
            suffixArray[charCount[s.charAt(i) - BASECHAR]] = i;
            charCount[s.charAt(i) - BASECHAR]++;
        }

        // Divide suffixes into equivalence classes depending on their first letter
        int classN = 0;
        equivalenceClasses[suffixArray[0]] = classN; // first element will always be in the first class
        for (int i = 1; i < n; i++) {
            if (s.charAt(suffixArray[i]) != s.charAt(suffixArray[i - 1])) {
                classN++; // not equal, new class
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
            for (int i = 1; i <= classN; i++) {
                charCount[i] += charCount[i - 1];
            }

            // sort after classes (if of same class they dont swap. It's a stable sort)
            for (int i = 0; i < n; i++) {
                suffixArray[charCount[equivalenceClasses[tempSufixArray[i]]]] = tempSufixArray[i];
                charCount[equivalenceClasses[tempSufixArray[i]]]++;
            }
            // Assign new equivalence classes based on the sorted suffix array
            classN = 0;
            tempEquivalanceClasses[suffixArray[0]] = classN; // first element, first class
            for (int i = 1; i < n; i++) {
                int cur = equivalenceClasses[suffixArray[i]] * ALPHABET // first half
                        + equivalenceClasses[(suffixArray[i] + shift) % n]; // second half
                int prev = equivalenceClasses[suffixArray[i - 1]] * ALPHABET // first half
                        + equivalenceClasses[(suffixArray[i - 1] + shift) % n]; // second half
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

    // Runtime: O(N) with N = Length of string
    public static int findMaxLCP(String s, int[] sa) {
        int maxLCP = 0;
        int[] saIndexOf = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            saIndexOf[sa[i]] = i; // used to find the element in sa before the string of length i
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
        return maxLCP;
    }
}