package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "rawtypes"})
public abstract class BaseSort {

    protected static boolean less(@NotNull Comparable v, @NotNull Comparable w) {
        //noinspection unchecked
        return v.compareTo(w) < 0;
    }

    protected static void exch(Comparable @NotNull [] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    protected static boolean isSorted(Comparable @NotNull [] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }
}