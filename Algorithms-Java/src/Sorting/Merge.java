package Sorting;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"rawtypes", "unused"})
public class Merge extends BaseSort {

    private static final int CUT_OFF = 7;

    public static void sort(Comparable @NotNull [] a) {
        Comparable[] aux = new Comparable[a.length];
        // make a copy in auxiliary array.
        System.arraycopy(a,0, aux, 0, a.length);
        sort(aux, a, 0, a.length - 1);
    }

    @SuppressWarnings("DuplicatedCode")
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        assert isSorted(aux, lo, mid);        // pre-condition: aux[lo ... mid] left-half sorted
        assert isSorted(aux, mid + 1, hi);// pre-condition: aux[mid+1 ... hi] right-half sorted

        int i = lo; int j = mid + 1;

        // merge a into aux
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                aux[k] = a[j++];
            else if (j > hi)            aux[k] = a[i++];
            else if (less(a[j], a[i]))  aux[k] = a[j++];
            else                        aux[k] = a[i++];
        }

        assert isSorted(aux, lo, hi);     // post-condition: a[lo ... hi] should be sorted
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // Practical improvement #1: use Insertion sort in small sub-arrays
        if (hi <= lo + CUT_OFF - 1) {
            Insertion.sort(aux, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);

        // Practical improvement #2: stop if already sorted
        if (!less(aux[mid + 1], aux[mid])) return;

        // Practical improvement #3: switch roles of 'aux' and 'a' in each recursive call
        merge(a, aux, lo, mid, hi);
    }
}