package Sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static Common.CommonOperation.exch;
import static Common.CommonOperation.less;

@SuppressWarnings({"unused", "rawtypes"})
public class Heap extends BaseSort {

    private static final int OFFSET = -1; // Sink operation to work with 0-based index array;
    private static Comparator comparator;
    private static int N;

    private static void _sort(Object @NotNull [] a) {
        // Build-Heap Phase
        for (int i = N / 2; i >= 1; i--)
            sink(i, a);

        // Sort-Down Phase
        while (N >= 1) {
            exch(a, 1 + OFFSET, --N);
            sink(1, a);
        }
    }

    private static void sink(int k, Object[] a) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a[j + OFFSET], a[j + 1 + OFFSET], comparator)) j++;
            if (!less(a[k + OFFSET], a[j + OFFSET], comparator)) break;
            exch(a, k + OFFSET, j + OFFSET);
            k = j;
        }
    }

    /**
     * Heap-Sort by natural order
     *
     * @param a array of comparable
     */
    public static void sort(Comparable @NotNull [] a) {
        sort(a, null);
    }

    /**
     * Heap-Sort by alternate order
     *
     * @param a          array of object
     * @param comparator alternate sort order
     */
    public static void sort(Object[] a, Comparator comparator) {
        Heap.comparator = comparator;
        N = a.length;
        _sort(a);
        assert isSorted(a, comparator);
    }
}