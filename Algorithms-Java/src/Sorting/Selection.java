package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes"})
public class Selection extends BaseSort {

    private static Comparator comparator;

    public static void _sort(Object @NotNull [] a, int lo, int hi) {
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        int n = a.length;
        for (int i = lo; i <= hi; i++) {
            int min = i;
            for (int j = i + 1; j <= hi; j++)
                if (less(a[j], a[min], comparator)) min = j;

            exch(a, i, min);
        }
    }

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
    public static void sort(Object @NotNull [] a, int lo, int hi, Comparator comparator) {
        Selection.comparator = comparator;
        _sort(a, lo, hi);
    }

    /**
     * @param a array of comparable
     */
    public static void sort(Comparable @NotNull [] a) {
        sort(a, 0, a.length - 1, null);
    }

    /**
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object @NotNull [] a, Comparator comparator) {
        Selection.comparator = comparator;
        _sort(a, 0, a.length - 1);
    }
}