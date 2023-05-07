import java.util.NoSuchElementException;

public class ArrayStack<T> {

    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the data to the top of the stack.
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (size == backingArray.length) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i] = backingArray[i];
            }
            newArr[size] = data;
            backingArray = newArr;
            size++;
        } else {
            backingArray[size] = data;
            size++;
        }
    }

    /**
     * Removes and returns the data from the top of the stack.
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty!");
        }
        T item = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return item;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty!");
        } else {
            return backingArray[size - 1];
        }
    }

    /**
     * Returns the backing array of the stack.
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     * @return the size of the stack
     */
    public int size() {
        return size;
    }
}