import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     */
    public MaxHeap() {
        this.size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data or any element in dat is null!");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        backingArray[0] = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data or any element in dat is null!");
            } else {
                backingArray[i + 1] = data.get(i);
            }
        }
        for (int j = size / 2; j >= 1; j--) {
            downHeap(j);
        }
    }

    /**
     * Adds the data to the heap.
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (isEmpty()) {
            size++;
            backingArray[1] = data;
        } else if (backingArray.length == size + 1) {
            T[] newArr = (T[]) new Comparable[backingArray.length * 2];
            newArr[0] = null;
            for (int i = 0; i < this.size; i++) {
                newArr[i + 1] = backingArray[i + 1];
            }
            backingArray = newArr;
            upHeap(data);
        } else {
            upHeap(data);
        }
    }

    /**
     * Removes and returns the root of the heap.
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty!");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size -= 1;
        downHeap(1);
        return removed;
    }

    /**
     * Helps to heap up by adding the new node.
     * @param upData data to be upheaped
     * @return backingArray upHeap result
     */
    private T[] upHeap(T upData) {
        size++;
        backingArray[size] = upData;
        int i = size;
        while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) > 0) {
            T changed = backingArray[i / 2];
            backingArray[i / 2] = backingArray[i];
            backingArray[i] = changed;
            i /= 2;
        }
        return backingArray;
    }

    /**
     * Helps to heap down.
     * @param curr the current index
     * @return backingArray downHeap result
     */
    private T[] downHeap(int curr) {
        while (curr * 2 <= size) {
            int index = curr * 2;
            if (index <= (size - 1) && (backingArray[index + 1].compareTo(backingArray[index]) > 0)) {
                index += 1;
            }
            if (backingArray[curr].compareTo(backingArray[index]) < 0) {
                T change = backingArray[curr];
                backingArray[curr] = backingArray[index];
                backingArray[index] = change;
            } else {
                return backingArray;
            }
            curr = index;
        }
        return backingArray;
    }

    /**
     * Returns the maximum element in the heap.
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty!");
        }
        T maximum = backingArray[1];
        return maximum;
    }

    /**
     * Returns whether or not the heap is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     */
    public void clear() {
        this.size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}