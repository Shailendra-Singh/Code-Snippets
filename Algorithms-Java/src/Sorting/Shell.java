package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static Common.CommonOperation.exch;
import static Common.CommonOperation.less;

@SuppressWarnings({"unused", "rawtypes"})
public class Shell extends BaseSort {

    private static Comparator comparator;

    private static void _sort(Object @NotNull [] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) h = 3 * h + 1; // Sequence: 3x + 1 => 1, 4, 13, 40, 121, ...

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h], comparator); j -= h)
                    exch(a, j, j - h);
            }

            h /= 3;
        }
    }

    /**
     * Shell-Sort using 3x + 1 series.
     * Maintains an invariant of h-sorted using natural order.
     *
     * @param a array of comparable
     */
    public static void sort(Comparable @NotNull [] a) {
        sort(a, null);
    }

    /**
     * Shell-Sort using 3x + 1 series.
     * Maintains an invariant of h-sorted using alternate order.
     *
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object @NotNull [] a, Comparator comparator) {
        Shell.comparator = comparator;
        _sort(a);
    }
}