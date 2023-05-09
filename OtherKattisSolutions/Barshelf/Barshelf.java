import java.util.*;

public class Barshelf {
    static int n;
    static int[] segmentTree;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int[] bottles = new int[n];
        for (int i = 0; i < n; i++) {
            bottles[i] = scanner.nextInt();
        }

        ArrayList<Integer> sortedBottlesWithNeighbours = new ArrayList<>();
        for (var bottle : bottles) {
            sortedBottlesWithNeighbours.add(bottle);
            sortedBottlesWithNeighbours.add(bottle / 2);
            sortedBottlesWithNeighbours.add(bottle * 2);
        }
        Collections.sort(sortedBottlesWithNeighbours);

        HashMap<Integer, Integer> heightToIndex = new HashMap<>();
        for (int i = 0; i < sortedBottlesWithNeighbours.size(); i++) {
            heightToIndex.put(sortedBottlesWithNeighbours.get(i), i);
        }

        segmentTree = new int[2 * sortedBottlesWithNeighbours.size()];
        long[] messyTrios = new long[n];
        for (int i = 0; i < n; i++) { // left to right
            var bottle = bottles[i];
            add(heightToIndex.get(bottle), 1);
            messyTrios[i] = sum(heightToIndex.get(bottle * 2),
                    heightToIndex.get(sortedBottlesWithNeighbours.get(sortedBottlesWithNeighbours.size() - 1))); // how
                                                                                                                 // many
                                                                                                                 // bottles
                                                                                                                 // of
                                                                                                                 // height
                                                                                                                 // >=
                                                                                                                 // 2*b
                                                                                                                 // have
                                                                                                                 // we
                                                                                                                 // passed
        }
        // now all bottles knows how many previous bottles were at least double height

        segmentTree = new int[2 * sortedBottlesWithNeighbours.size()];

        for (int i = bottles.length - 1; i >= 0; i--) { // right to left
            var bottle = bottles[i];
            add(heightToIndex.get(bottle), 1);
            messyTrios[i] *= sum(1, heightToIndex.get(bottle / 2)); // how many bottles of height <= bottle/2 have we
                                                                    // passed
        }

        System.out.println(Arrays.stream(messyTrios).sum());
    }

    private static int sum(int from, int to) { // inclusive
        from += segmentTree.length / 2;
        to += segmentTree.length / 2;
        int s = 0;
        while (from <= to) {
            if (from % 2 == 1)
                s += segmentTree[from++];
            if (to % 2 == 0)
                s += segmentTree[to--];
            from /= 2;
            to /= 2;
        }
        return s;
    }

    private static void add(int index, int amount) {
        index += segmentTree.length / 2;
        segmentTree[index] += amount;
        for (index /= 2; index >= 1; index /= 2) {
            segmentTree[index] = segmentTree[2 * index] + segmentTree[2 * index + 1];
        }
    }
}
