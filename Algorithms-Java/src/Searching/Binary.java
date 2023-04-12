package Searching;

import Sorting.BaseSort;

import java.util.Comparator;

import static Common.CommonOperation.compareTo;

/**
 * Requires the input-array to be sorted, hence depends on BaseSort for validity check.
 */
@SuppressWarnings({"unused", "rawtypes"})
public class Binary extends BaseSort {
    public static boolean search(Comparable[] a, Comparable key) {
        return search(a, key, null);
    }

    public static boolean search(Object[] a, Object key, Comparator comparator) {
        if (a == null || key == null || !isSorted(a, null))
            throw new IllegalArgumentException("Array should be sorted.");
        int i = rank(a, key, comparator);
        return i < a.length && compareTo(a[i], key, comparator) == 0;
    }

    public static int rank(Object[] a, Object k, Comparator comparator) {

        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compareTo(k, a[mid], comparator);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }
}