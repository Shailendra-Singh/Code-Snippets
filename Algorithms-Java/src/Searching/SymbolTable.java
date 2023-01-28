package Searching;

/**
 * Unordered abstraction of Symbol Table API
 *
 * @param <Key>   Symbol Table's Comparable Key
 * @param <Value> Value associated with Key
 */

@SuppressWarnings("unused")
abstract class SymbolTable<Key, Value> {
    /**
     * put key-value pair into the table
     * (remove key from table if value is null)
     *
     * @param key   item key
     * @param value value associated with given key
     */
    abstract void put(Key key, Value value);

    /**
     * @param key item key
     * @return value paired with key (null if key is absent)
     */
    abstract Value get(Key key);

    /**
     * @param key remove key (and its value) from table
     */
    abstract void delete(Key key);

    /**
     * @param key item key
     * @return is there a value paired with key?
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * @return is the table empty?
     */
    abstract boolean isEmpty();

    /**
     * @return number of key-value pairs
     */
    abstract int size();

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return all keys in the table
     */
    abstract Iterable<Key> keys();
}