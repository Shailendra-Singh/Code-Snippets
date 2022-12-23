package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public class Insertion extends BaseSort {

    public static void sort(Comparable @NotNull [] a, int lo, int hi) {
        if (lo < 0) throw new IllegalArgumentException("Illegal lower bound");
        if (hi > a.length - 1) throw new IllegalArgumentException("Illegal upper bound");
        for (int i = lo+1; i <= hi; i++)
            for (int j = i; j > lo; j--)
                if (less(a[j], a[j - 1])) exch(a, j, j - 1);
                else break;
    }

    public static void sort(Comparable @NotNull [] a){
        sort(a, 0, a.length-1);
    }
}