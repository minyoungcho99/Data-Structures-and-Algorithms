import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExternalChainingHashMap<K, V> {

    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     * @param capacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int capacity) {
        size = 0;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[capacity];        
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null!");
        }
        if ((double) (size + 1) / (double) table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        ExternalChainingMapEntry<K, V> add = new ExternalChainingMapEntry<>(key, value);
        V replace = null;
        int hashing = Math.abs(key.hashCode() % table.length);
        if (table[hashing] == null) {
            table[hashing] = add;
            size++;
        } else {
            ExternalChainingMapEntry<K, V> curr = table[hashing];
            while (curr.getNext() != null && !curr.getKey().equals(key)) {
                curr = curr.getNext();
            }
            if (curr.getKey().equals(key)) {
                replace = curr.getValue();
                curr.setValue(value);
                return replace;
            } else {
                add.setNext(table[hashing]);
                table[hashing] = add;
                size++;
            }
        }
        return replace;
    }

    /**
     * Removes the entry with a matching key from the map.
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null!");
        }
        int hashing = Math.abs(key.hashCode() % table.length);
        V removed = null;
        if (table[hashing] != null) {
            ExternalChainingMapEntry<K, V> curr = table[hashing];
            ExternalChainingMapEntry<K, V> prev = null;
            if (curr.getKey().equals(key)) {
                removed = table[hashing].getValue();
                table[hashing] = curr.getNext();
                size--;
                return removed;
            }
            while (curr.getNext() != null && !curr.getKey().equals(key)) {
                prev = curr;
                curr = curr.getNext();
            }
            if (curr.getKey().equals(key)) {
                removed = curr.getValue();
                if (prev != null) {
                    prev.setNext(curr.getNext());
                }
                size--;
                return removed;
            }
        }
        throw new NoSuchElementException("Key is not in the map!");
    }

    /**
     * Gets the value associated with the given key.
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null!");
        } else if (size == 0) {
            throw new NoSuchElementException("Key is not in the map!");
        }
        int hashing = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> curr = table[hashing];
        if (curr != null) {
            while (curr != null && !curr.getKey().equals(key)) {
                curr = curr.getNext();
            }
            if (curr != null) {
                return curr.getValue();
            }
        }
        throw new NoSuchElementException("Key is not in the map!");
    }

    /**
     * Returns whether or not the key is in the map.
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        try {
            get(key);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        if (size == 0) {
            return keys;
        }
        for (ExternalChainingMapEntry curr : table) {
            if (curr != null) {
                keys.add((K) curr.getKey());
                while (curr.getNext() != null) {
                    curr = curr.getNext();
                    keys.add((K) curr.getKey());
                }
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> data = new ArrayList<>();
        if (size == 0) {
            return data;
        }
        for (ExternalChainingMapEntry curr : table) {
            if (curr != null) {
                data.add((V) curr.getValue());
                while (curr.getNext() != null) {
                    curr = curr.getNext();
                    data.add((V) curr.getValue());
                }
            }
        }
        return data;
    }

    /**
     * Resize the backing table to length.
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length is less than the number of items!");
        }
        ExternalChainingMapEntry<K, V>[] newTable = new ExternalChainingMapEntry[length];
        int newSize = 0;
        for (ExternalChainingMapEntry<K, V> curr : table) {
            if (curr != null) {
                int hashing = Math.abs(curr.getKey().hashCode() % length);
                if (newTable[hashing] == null) {
                    newTable[hashing] = curr;
                    newSize++;
                } else {
                    curr.setNext(newTable[hashing]);
                    newTable[hashing] = curr;
                    newSize++;
                }
            }
        }
        size = newSize;
        table = newTable;
    }

    /**
     * Clears the map.
     */
    public void clear() {
        size = 0;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        return table;
    }

    /**
     * Returns the size of the map.
     * @return the size of the map
     */
    public int size() {
        return size;
    }
}
