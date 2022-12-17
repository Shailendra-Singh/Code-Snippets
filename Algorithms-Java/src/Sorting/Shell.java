package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "rawtypes"})
public class Shell extends BaseSort {

    public static void sort(Comparable @NotNull [] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) h = 3 * h + 1; // Sequence: 3x + 1 => 1, 4, 13, 40, 121, ...

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            }

            h /= 3;
        }
    }
}