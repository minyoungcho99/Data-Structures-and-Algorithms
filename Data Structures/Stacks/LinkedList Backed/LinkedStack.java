import java.util.NoSuchElementException;

public class LinkedStack<T> {

    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the data to the top of the stack.
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (size == 0) {
            head = new LinkedNode<>(data);
            size = 1;
        } else {
            head = new LinkedNode<>(data, head);
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
        T item =  head.getData();
        head = head.getNext();
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
        }
        return head.getData();
    }

    /**
     * Returns the head node of the stack.
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the size of the stack.
     * @return the size of the stack
     */
    public int size() {
        return size;
    }
}