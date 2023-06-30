import java.util.*;
import java.io.*;


public class Main {
    public static void main(String args[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        // read in all the intervals into ans.
        ArrayList<Interval> ans = new ArrayList<Interval>();
        for (int i = 0; i < num; i++) {
            try {
                ans.add(new Interval(scan.nextInt(), scan.nextInt()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid interval");
            }


        }


        Interval.condenseAndSort(ans);
        // print out the answer.
        for (Interval a : ans) {
            System.out.println(a.start + " " + a.end);
        }
        scan.close();
    }


    private static class Interval implements Comparable<Interval> {
        private int start;
        private int end;


        /**
         * Creates a new Interval that represents [s,e]
         * @throws IllegalArgumentException if the end parameter is less than the start.
         */
        public Interval(int s, int e) throws IllegalArgumentException {
            if (e < s) {
                throw new IllegalArgumentException();
            }
            start = s;
            end = e;
        }


        public void setStart(int s) {
            start = s;
        }


        public void setEnd(int e) {
            end = e;
        }


        public int compareTo(Interval b) {
            return this.start - b.start;
        }


        /**
         * Condenses the array.
         * Condenses means sorting the array by start values of each Interval object
         * in the ArrayList and then merging
         * overlapping Intervals objects
         * (overlapping means when some value x is contained within two or more
         * intervals).
         * At the end, for any given value x, x is in at most one interval in the
         * ArrayList
         * @param ...
         * @return Void. The original ArrayList of Intervals that is passed in as a parameter gets condensed.
         */
        public static void condenseAndSort(ArrayList<Interval> ans) {
            Collections.sort(ans);
            // merge the intervals. This particular way of merging only works on a sorted
            // list.
            for (int i = 0; i < ans.size() - 1; i++) {
                if (combine(ans, i, i + 1, intersect(ans.get(i), ans.get(i + 1)))) {
                    i--;
                }
            }
        }


    }


    /**
     * Determines if Interval {@code a} intersects (overlaps) Interval {@code b}.
     * Only works if Interval {@code a} is before Interval {@code b}
     * @param ...
     * @return A value from the Overlap enum, one of four ways the intervals could
     *         be overlapping:
     *         <blockquote>
     *
     *         <pre>
     *START: the start of b is in the middle of a.
     *END: the end of b is in the middle of a.
     *COMPLETE: b is entirely inside of a.
     *NONE: b has no overlap with a.
     *         </pre>
     *
     *         </blockquote>
     */
    public static Overlap intersect(Interval a, Interval b) {
        if (b.start >= a.start && b.end <= a.end) {
            return Overlap.COMPLETE;
        }
        if (a.start <= b.start && b.start <= a.end) {
            return Overlap.START;
        }
        if (a.start <= b.end && b.end <= a.end) {
            return Overlap.END;
        }
        return Overlap.NONE;
    }


    /**
     * Combines the two intervals from {@code ans} at indices {@code i} and
     * {@code j} based on given information about how the intervals intersect
     *
     * @param ...
     * @return Whether or not the two intervals were combined.
     */
    public static boolean combine(ArrayList<Interval> ans, int i, int j, Overlap intersect) {
        switch (intersect) {
            case NONE:
                return false;
            case COMPLETE:
                ans.remove(j);
                break;
            case START:
                ans.get(j).setStart(ans.get(i).start);
                ans.remove(i);
                break;
            case END:
                ans.get(j).setEnd(ans.get(i).end);
                ans.remove(i);
                break;
        }
        return true;


    }


    private enum Overlap {
        START, // the start of one interval is in the middle of another interval
        END, // the end of one interval is in the middle of another interval
        COMPLETE, // one interval is entirely inside of another interval
        NONE; // one interval has no overlap with another interval
    }
}

