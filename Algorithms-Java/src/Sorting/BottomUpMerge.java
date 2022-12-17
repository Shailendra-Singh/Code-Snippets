package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "rawtypes"})
public class BottomUpMerge extends BaseSort {

    private static Comparable[] aux;

    @SuppressWarnings("DuplicatedCode")
    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);        // pre-condition: aux[lo ... mid] left-half sorted
        assert isSorted(a, mid + 1, hi);// pre-condition: aux[mid+1 ... hi] right-half sorted

        // Copy contents into auxiliary array
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        int i = lo; int j = mid + 1;

        // merge a into aux
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);     // post-condition: a[lo ... hi] should be sorted
    }

    public static void sort(Comparable @NotNull [] a) {
        int n = a.length;
        aux = new Comparable[n];
        for (int sz = 1; sz < n; sz += sz)
            for (int lo = 0; lo < n - sz; lo += sz + sz)
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, n - 1));
    }
}