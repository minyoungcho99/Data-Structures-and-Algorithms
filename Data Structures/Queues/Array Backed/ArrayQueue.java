import java.util.NoSuchElementException;

public class ArrayQueue<T> {

    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        size = 0;
    }

    /**
     * Adds the data to the back of the queue.
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (size == backingArray.length) {
            T[] newArr = (T[]) new Object[INITIAL_CAPACITY * 2];
            int num = front;
            for (int i = 0; i < size; i++) {
                newArr[i] = backingArray[num % backingArray.length];
                num++;
            }
            newArr[size] = data;
            backingArray = newArr;
            front = 0;
            size++;
        } else {
            backingArray[((size + front) % backingArray.length)] = data;
            size++;
        }
    }

    /**
     * Removes and returns the data from the front of the queue.
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        T item = backingArray[front];
        backingArray[front] = null;
        front = (1 + front) % backingArray.length;
        size--;
        return item;
    }

    /**
     * Returns the data from the front of the queue without removing it.
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the front index of the queue.
     * @return the front index of the queue
     */
    public int getFront() {
        return front;
    }

    /**
     * Returns the size of the queue.
     * @return the size of the queue
     */
    public int size() {
        return size;
    }
}
