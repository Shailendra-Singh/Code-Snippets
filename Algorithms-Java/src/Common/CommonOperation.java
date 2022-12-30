package Common;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public abstract class CommonOperation {
    protected static int compareTo(Comparable v, Comparable w) {
        return v.compareTo(w);
    }

    protected static int compareTo(Object v, Object w, Comparator c) {
        if (c == null) return compareTo((Comparable) v, (Comparable) w);

        return c.compare(v, w);
    }

    protected static boolean less(Object v, Object w, Comparator c) {
        if (c == null) return less((Comparable) v, (Comparable) w);

        return compareTo(v, w, c) < 0;
    }

    protected static boolean less(@NotNull Comparable v, @NotNull Comparable w) {
        return compareTo(v, w) < 0;
    }

    protected static void exch(Object[] a, int i, int j) {
        Object swap = a[i]; a[i] = a[j]; a[j] = swap;
    }
}
