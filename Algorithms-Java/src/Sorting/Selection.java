package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "rawtypes"})
public class Selection extends BaseSort {

    public static void sort(Comparable @NotNull [] a, int lo, int hi) {
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        int n = a.length;
        for (int i = lo; i <= hi; i++) {
            int min = i;
            for (int j = i + 1; j <= hi; j++)
                if (less(a[j], a[min])) min = j;

            exch(a, i, min);
        }
    }

    public static void sort(Comparable @NotNull [] a){
        sort(a, 0, a.length-1);
    }
}