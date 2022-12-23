package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public abstract class BaseSort {

    protected static int compareTo(Comparable v, Comparable w) {
        return v.compareTo(w);
    }

    protected static int compareTo(Object v, Object w, Comparator c) {
        if (c == null) return BaseSort.compareTo((Comparable) v, (Comparable) w);

        return c.compare(v, w);
    }

    protected static boolean less(Object v, Object w, Comparator c) {
        if (c == null) return BaseSort.less((Comparable) v, (Comparable) w);

        return BaseSort.compareTo(v, w, c) < 0;
    }

    protected static boolean less(@NotNull Comparable v, @NotNull Comparable w) {
        return BaseSort.compareTo(v, w) < 0;
    }

    protected static void exch(Object[] a, int i, int j) {
        Object swap = a[i]; a[i] = a[j]; a[j] = swap;
    }

    protected static boolean isSorted(Object @NotNull [] a, int lo, int hi) {
        return isSorted(a, lo, hi, null);
    }

    protected static boolean isSorted(Object @NotNull [] a, int lo, int hi, Comparator comparator) {
        for (int i = lo; i <= hi; i++)
            if (less(a[i], a[i - 1], comparator)) return false;
        return true;
    }
}