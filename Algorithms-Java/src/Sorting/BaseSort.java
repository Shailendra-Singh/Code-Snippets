package Sorting;

import Common.CommonOperation;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public abstract class BaseSort extends CommonOperation {
    protected static boolean isSorted(Object @NotNull [] a, int lo, int hi) {
        return isSorted(a, lo, hi, null);
    }

    protected static boolean isSorted(Object @NotNull [] a, int lo, int hi, Comparator comparator) {
        for (int i = lo; i <= hi; i++)
            if (less(a[i], a[i - 1], comparator)) return false;
        return true;
    }
}