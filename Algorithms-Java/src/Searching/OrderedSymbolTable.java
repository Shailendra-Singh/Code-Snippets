package Searching;

/**
 * Ordered abstraction of Symbol Table API
 *
 * @param <Key>   Symbol Table's Comparable Key
 * @param <Value> Value associated with Key
 */
@SuppressWarnings({"unused"})
abstract class OrderedSymbolTable<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value> {
    /**
     * @return smallest Comparable
     */
    abstract Key min();

    /**
     * @return largest Comparable
     */
    abstract Key max();

    /**
     * @param key item Comparable
     * @return largest Comparable less than or equal to Comparable
     */
    abstract Key floor(Key key);

    /**
     * @param key item Comparable
     * @return smallest Comparable greater than or equal to Comparable
     */
    abstract Key ceiling(Key key);

    /**
     * @param key item Comparable
     * @return number of keys less than Comparable
     */
    abstract int rank(Key key);

    /**
     * @param k rank
     * @return Comparable of rank k
     */
    abstract Key select(int k);

    /**
     * delete smallest Comparable
     */
    abstract void deleteMin();

    /**
     * delete largest Comparable
     */
    abstract void deleteMax();

    /**
     * @return number of keys in symbol table
     */
    abstract int size();

    /**
     * @param lo Comparable lower-bound
     * @param hi Comparable upper-bound
     * @return number of keys in [lo..hi]
     */
    abstract int size(Key lo, Key hi);

    /**
     * @param lo Comparable lower-bound
     * @param hi Comparable upper-bound
     * @return keys in [lo..hi], in sorted order
     */
    abstract Iterable<Key> keys(Key lo, Key hi);
}