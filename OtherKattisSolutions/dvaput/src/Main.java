import java.util.*;

public class Main {

    static int[] sa;
    static int[] lcp;
    static int maxLCP = 0;

    public static void main(String[] args) {

        var scanner = new java.util.Scanner(System.in);
        scanner.nextInt();
        scanner.nextLine();
        var inputString = scanner.nextLine();
        sa = suffixArrayConstruction(inputString);
        lcp = constructLCPArray(inputString, sa);
        System.out.println(maxLCP);
        scanner.close();
    }

    // 100 % his solution
    // https://github.com/stevenhalim/cpbook-code/blob/master/ch6/sa_lcp.py
    // Sorting with cyclic shifts
    public static int[] suffixArrayConstruction(String s) {
        return Arrays.copyOfRange(sortCyclicShifts(s + '\0'), 1, s.length() + 1);
    }

    public static int[] sortCyclicShifts(String s) {
        int n = s.length();
        int alphabet = 256;
        int[] suffixArray = new int[n];
        int[] equivalenceClasses = new int[n];
        int[] charCount = new int[Math.max(alphabet, n)];

        // Count the number of occurrences of each character in the input string
        for (int i = 0; i < n; i++) {
            charCount[s.charAt(i)]++;
        }
        // Calculate the starting index for each character in the sorted suffix array
        for (int i = 1; i < alphabet; i++) {
            charCount[i] += charCount[i - 1];
        }
        // Construct the sorted suffix array
        for (int i = n - 1; i >= 0; i--) {
            charCount[s.charAt(i)]--;
            suffixArray[charCount[s.charAt(i)]] = i;
        }

        // Assign equivalence classes to each suffix
        equivalenceClasses[suffixArray[0]] = 0;
        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(suffixArray[i]) != s.charAt(suffixArray[i - 1])) {
                classes++;
            }
            equivalenceClasses[suffixArray[i]] = classes - 1;
        }

        // Perform multiple rounds of sorting, increasing the suffix size each time
        int[] tempSufixArray = new int[n];
        int[] tempEquivalanceClasses = new int[n];
        for (int h = 0; (1 << h) < n; h++) {

            for (int i = 0; i < n; i++) {
                tempSufixArray[i] = suffixArray[i] - (1 << h);
                if (tempSufixArray[i] < 0) {
                    tempSufixArray[i] += n;
                }
            }
            Arrays.fill(charCount, 0);
            for (int i = 0; i < n; i++) {
                charCount[equivalenceClasses[tempSufixArray[i]]]++;
            }
            for (int i = 1; i < classes; i++) {
                charCount[i] += charCount[i - 1];
            }

            // Construct the sorted suffix array based on the current equivalence classes
            for (int i = n - 1; i >= 0; i--) {
                charCount[equivalenceClasses[tempSufixArray[i]]]--;
                suffixArray[charCount[equivalenceClasses[tempSufixArray[i]]]] = tempSufixArray[i];
            }
            // Assign new equivalence classes based on the sorted suffix array
            tempEquivalanceClasses[suffixArray[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; i++) {
                int cur = equivalenceClasses[suffixArray[i]] * alphabet
                        + equivalenceClasses[(suffixArray[i] + (1 << h)) % n];
                int prev = equivalenceClasses[suffixArray[i - 1]] * alphabet
                        + equivalenceClasses[(suffixArray[i - 1] + (1 << h)) % n];
                if (cur != prev) {
                    classes++;
                }
                tempEquivalanceClasses[suffixArray[i]] = classes - 1;
            }
            // Swap the equivalence class arrays for the next round of sorting
            int[] temp = equivalenceClasses;
            equivalenceClasses = tempEquivalanceClasses;
            tempEquivalanceClasses = temp;
        }
        return suffixArray;
    }

    // Runtime: O(N) - N : Length of string
    public static int[] constructLCPArray(String s, int[] sa) {
        int n = s.length();
        int[] lcp = new int[n - 1];
        int[] invSa = new int[n];
        maxLCP = 0; // initialize maxLCPFound to 0
        for (int i = 0; i < n; i++) {
            invSa[sa[i]] = i;
        }
        int len = 0;
        char[] chars = s.toCharArray(); // Use char array for indexing
        for (int i = 0; i < n; i++) {
            if (invSa[i] == n - 1) {
                len = 0;
                continue;
            }
            int j = sa[invSa[i] + 1];
            int k = i + len;
            int m = j + len;
            while (k < n && m < n && chars[k] == chars[m]) { // Use temporary variables for array indexing
                len++;
                k++;
                m++;
                if (len > maxLCP) { // update maxLCPFound if len is greater
                    maxLCP = len;
                }
            }
            lcp[invSa[i]] = len;
            if (len > 0) {
                len--;
            }
        }
        return lcp;
    }

    // Finds that actual string, that is the longest repeating substring
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