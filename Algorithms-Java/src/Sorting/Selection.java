package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "rawtypes"})
public class Selection extends BaseSort {

    public static void sort(Comparable @NotNull [] a, int lo, int hi) {
        int n = a.length;
        for (int i = lo; i <= hi; i++) {
            int min = i;
            for (int j = i + 1; j <= hi; j++)
                if (less(a[j], a[min])) min = j;

            exch(a, i, min);
        }
    }
}