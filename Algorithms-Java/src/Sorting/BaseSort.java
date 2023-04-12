package Sorting;

import static Common.CommonOperation.less;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes", "SameParameterValue"})
public abstract class BaseSort {

    protected static boolean isSorted(Object @NotNull [] a) {
        return isSorted(a, 0, a.length);
    }

    protected static boolean isSorted(Object @NotNull [] a, Comparator comparator) {
        return isSorted(a, 0, a.length, comparator);
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