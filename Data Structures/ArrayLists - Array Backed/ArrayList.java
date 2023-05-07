import java.util.NoSuchElementException;

public class ArrayList<T> {

    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Input index is out of bounds!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Input data is null!");
        }
        if (index == size) {
            addToBack(data);
        } else if (size >= backingArray.length) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < index; i++) {
                newArr[i] = backingArray[i];
            }
            newArr[index] = data;
            for (int i = index; i < backingArray.length; i++) {
                newArr[i + 1] = backingArray[i];
            }
            backingArray = newArr;
            size++;
        } else {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null!");
        }
        if (backingArray.length <= size) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i + 1] = backingArray[i];
            }
            newArr[0] = data;
            backingArray = newArr;
        } else {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null!");
        }
        if (backingArray.length <= size) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i] = backingArray[i];
            }
            newArr[size] = data;
            backingArray = newArr;
        } else {
            backingArray[size] = data;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Input index is out of bounds!");
        }
        T remove = backingArray[index];
        if (index == size - 1) {
            removeFromBack();
        } else {
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
        }
        return remove;
    }

    /**
     * Removes and returns the first element of the list.
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty!");
        }
        T remove = backingArray[0];
        for (int i = 1; i < size; i++) {
            backingArray[i - 1] = backingArray[i];
        }
        backingArray[size - 1] = null;
        size--;
        return remove;
    }

    /**
     * Removes and returns the last element of the list.
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The list is empty!");
        }
        T remove = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return remove;
    }

    /**
     * Returns the element at the specified index.
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Input index is out of bounds!");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the list.
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}