package Sorting;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

import static Common.CommonOperation.*;

@SuppressWarnings({"rawtypes", "unused"})
public class Quick extends BaseSort {

    private static final int CUT_OFF = 7;
    private static Comparator comparator;

    /**
     * @param a  array of comparable
     * @param lo lower bound within array
     * @param hi upper bound within array
     */
    public static void sort(Comparable[] a, int lo, int hi) {
        sort(a, lo, hi, null);
    }

    /**
     * @param a          array of object
     * @param lo         lower bound within array
     * @param hi         upper bound within array
     * @param comparator alternate sort order
     */
    public static void sort(Object[] a, int lo, int hi, Comparator comparator) {
        Quick.comparator = comparator;
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        StdRandom.shuffle(a, lo, hi); // shuffle needed for performance guarantee
        recursive_sort(a, lo, hi);
    }

    /**
     * Performance tuned for input having large number of duplicate keys.
     *
     * @param a  array of comparable
     * @param lo lower bound within array
     * @param hi upper bound within array
     */
    public static void sort3way(Comparable[] a, int lo, int hi) {
        sort3way(a, lo, hi, null);
    }

    /**
     * Performance tuned for input having large number of duplicate keys.
     *
     * @param a          array of object
     * @param lo         lower bound within array
     * @param hi         upper bound within array
     * @param comparator alternate sort order
     */
    public static void sort3way(Object[] a, int lo, int hi, Comparator comparator) {
        Quick.comparator = comparator;
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        StdRandom.shuffle(a, lo, hi); // shuffle needed for performance guarantee
        partition3way(a, lo, hi);
    }

    /**
     * @param a array of comparable
     */
    public static void sort(Comparable[] a) {
        sort(a, null);
    }

    /**
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object[] a, Comparator comparator) {
        Quick.comparator = comparator;
        sort(a, 0, a.length - 1, comparator);
    }

    /**
     * Performance tuned for input having large number of duplicate keys.
     *
     * @param a array of comparable
     */
    public static void sort3way(Comparable[] a) {
        sort3way(a, null);
    }

    /**
     * Performance tuned for input having large number of duplicate keys.
     *
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort3way(Object[] a, Comparator comparator) {
        Quick.comparator = comparator;
        sort3way(a, 0, a.length - 1, comparator);
    }

    /**
     * @param a array of comparable
     * @param k value of k for kth ordered item
     * @return kth ordered item using natural order
     */
    public static Comparable select(Comparable[] a, int k) {
        return (Comparable) select(a, k, null);
    }

    /**
     * @param a          array of object
     * @param k          value of k for kth ordered item
     * @param comparator alternate sort order
     * @return kth ordered item using alternate order
     */
    public static Object select(Object[] a, int k, Comparator comparator) {
        Quick.comparator = comparator;
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }

    private static int partition(Object[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo], comparator)) if (i == hi) break; // find item on left to swap
            while (less(a[lo], a[--j], comparator)) if (j == lo) break; // find item on right to swap
            if (i >= j) break;                                          // check if pointers cross
            exch(a, i, j);                                              // swap
        }
        exch(a, lo, j);                                                 // swap with partitioning item
        return j;                                                       // return partitioning item's index
    }

    private static void partition3way(Object[] a, int lo, int hi) {
        if (hi <= lo) return;
        int i = lo, lt = lo, gt = hi;
        Object v = a[lo];
        while (i <= gt) {
            int cmp = compareTo(a[i], v, comparator);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        partition3way(a, lo, lt - 1);
        partition3way(a, gt + 1, hi);
    }

    private static void recursive_sort(Object[] a, int lo, int hi) {
        // Practical improvement #1: use Insertion sort in small sub-arrays
        if (hi <= lo + CUT_OFF - 1) {
            Insertion.sort(a, lo, hi, comparator);
            return;
        }

        // Practical improvement #2: use median of lo, mid, and hi for three random items
        // *not-implemented*

        int p = partition(a, lo, hi);
        recursive_sort(a, lo, p - 1);
        recursive_sort(a, p + 1, hi);
    }
}