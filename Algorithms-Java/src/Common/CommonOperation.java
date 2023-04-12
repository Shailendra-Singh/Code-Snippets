package Common;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public final class CommonOperation {
    public static int compareTo(Comparable v, Comparable w) {
        return v.compareTo(w);
    }

    public static int compareTo(Object v, Object w, Comparator c) {
        if (c == null) return compareTo((Comparable) v, (Comparable) w);

        return c.compare(v, w);
    }

    public static boolean less(Object v, Object w, Comparator c) {
        if (c == null) return less((Comparable) v, (Comparable) w);

        return compareTo(v, w, c) < 0;
    }

    public static boolean less(@NotNull Comparable v, @NotNull Comparable w) {
        return compareTo(v, w) < 0;
    }

    public static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
