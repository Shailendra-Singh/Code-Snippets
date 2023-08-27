package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static Common.CommonOperation.less;

@SuppressWarnings({"rawtypes", "unused"})
public class Merge extends BaseSort {

    private static final int CUT_OFF = 7;
    private static Comparator comparator;

    private static void _sort(Object @NotNull [] a) {
        Object[] aux = new Object[a.length];
        // make a copy in auxiliary array.
        System.arraycopy(a, 0, aux, 0, a.length);
        sort(aux, a, 0, a.length - 1);
    }

    private static void bottom_up(Object[] a, Object[] aux, int lo, int mid, int hi) {
        // Copy contents into auxiliary array (instead of switching roles of a and aux)
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);
        merge(aux, a, lo, mid, hi);
    }

    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi) {

        assert isSorted(aux, lo, mid, comparator);        // pre-condition: aux[lo ... mid] left-half sorted
        assert isSorted(aux, mid + 1, hi, comparator);// pre-condition: aux[mid+1 ... hi] right-half sorted

        int i = lo;
        int j = mid + 1;

        // merge a into aux
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = a[j++];
            else if (j > hi) aux[k] = a[i++];
            else if (less(a[j], a[i], comparator)) aux[k] = a[j++];
            else aux[k] = a[i++];
        }

        assert isSorted(aux, lo, hi, comparator);     // post-condition: a[lo ... hi] should be sorted
    }

    private static void sort(Object[] a, Object[] aux, int lo, int hi) {
        // Practical improvement #1: use Insertion sort in small sub-arrays
        if (hi <= lo + CUT_OFF - 1) {
            Insertion.sort(aux, lo, hi, comparator);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);

        // Practical improvement #2: stop if already sorted
        // *not implemented* because #3 is already implemented

        // Practical improvement #3: switch roles of 'aux' and 'a' in each recursive call
        merge(a, aux, lo, mid, hi);
    }

    /**
     * Merge-Sort by natural order using recursive implementation
     *
     * @param a array of comparable
     */
    public static void sort(Comparable @NotNull [] a) {
        sort(a, null);
    }

    /**
     * Merge-Sort by alternate order using recursive implementation
     *
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object[] a, Comparator comparator) {
        Merge.comparator = comparator;
        _sort(a);
    }

    /**
     * Uses non-recursive version of merge sort by merging bottom-up.
     * Bottom-up merge sorts sub-arrays of size of 1, 2, 4, 8, ... and so on.
     * using natural order
     *
     * @param a array of comparable
     */
    public static void non_recursive_sort(Comparable @NotNull [] a) {
        non_recursive_sort(a, null);
    }

    /**
     * Uses non-recursive version of merge sort by merging bottom-up.
     * Bottom-up merge sorts sub-arrays of size of 1, 2, 4, 8, ... and so on.
     * using alternate order
     *
     * @param a          array of objects
     * @param comparator alternate sort order
     */
    public static void non_recursive_sort(Object @NotNull [] a, Comparator comparator) {
        Merge.comparator = comparator;
        int n = a.length;
        Object[] aux = new Object[n];
        for (int sz = 1; sz < n; sz += sz)
            for (int lo = 0; lo < n - sz; lo += sz + sz)
                bottom_up(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, n - 1));
    }
}