package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static Common.CommonOperation.exch;
import static Common.CommonOperation.less;

@SuppressWarnings({"rawtypes", "unused"})
public class Insertion extends BaseSort {

    private static Comparator comparator;

    private static void _sort(Object @NotNull [] a, int lo, int hi) {
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        for (int i = lo + 1; i <= hi; i++)
            for (int j = i; j > lo; j--)
                if (less(a[j], a[j - 1], comparator)) exch(a, j, j - 1);
                else break;
    }

    /**
     * @param a array of comparable
     * @param lo lower bound within array
     * @param hi upper bound within array
     */
    public static void sort(Comparable[] a, int lo, int hi) {
        sort(a, lo, hi, null);
    }

    /**
     * @param a array of object
     * @param lo lower bound within array
     * @param hi upper bound within array
     * @param comparator alternate sort order
     */
    public static void sort(Object @NotNull [] a, int lo, int hi, Comparator comparator) {
        Insertion.comparator = comparator;
        _sort(a, lo, hi);
    }

    /**
     * @param a array of comparable
     */
    public static void sort(Comparable @NotNull [] a){
        sort(a, 0, a.length-1, null);
    }

    /**
     * @param a array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object[] a, Comparator comparator){
        Insertion.comparator = comparator;
        _sort(a, 0, a.length -1 );
    }
}