import java.util.NoSuchElementException;

public class LinkedQueue<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the back of the queue.
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (size == 0) {
            LinkedNode<T> newNode = new LinkedNode<>(data);
            head = newNode;
            tail = head;
            size++;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(data);
            tail.setNext(newNode);
            tail = newNode;
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
        } else if (size == 1) {
            T item = head.getData();
            head = null;
            tail = null;
            size = 0;
            return item;
        } else {
            T item = head.getData();
            head = head.getNext();
            size--;
            return item;
        }
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
        return head.getData();
    }

    /**
     * Returns the head node of the queue.
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the queue.
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        return tail;
    }

    /**
     * Returns the size of the queue.
     * @return the size of the queue
     */
    public int size() {
        return size;
    }
}