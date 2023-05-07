import java.util.NoSuchElementException;

public class CircularSinglyLinkedList<T> {

    private CircularSinglyLinkedListNode<T> head;
    private int size;
    
    /**
     * Adds the data to the specified index.
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data, curr.getNext());
            curr.setNext(newNode);
            size++;
        }



    }

    /**
     * Adds the data to the front of the list.
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0)  {
            head = new CircularSinglyLinkedListNode<T>(data);
            head.setNext(head);
            size++;
        } else {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(head.getData());
            newNode.setData(head.getData());
            newNode.setNext(head.getNext());
            head.setData(data);
            head.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (size == 0) {
            head = new CircularSinglyLinkedListNode<T>(data);
            head.setNext(head);
            size++;
        } else {
            CircularSinglyLinkedListNode<T> newNode =
                    new CircularSinglyLinkedListNode<T>(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size) {
            return removeFromBack();
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            T removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first data of the list.
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        } else if (size == 1) {
            T removed = head.getData();
            head = null;
            size = 0;
            return removed;
        } else {
            T removed = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the last data of the list.
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        } else if (size == 1) {
            T removed = head.getData();
            size = 0;
            head = null;
            return removed;
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < size - 2; i++) {
                curr = curr.getNext();
            }
            T removed = curr.getNext().getData();
            curr.setNext(head);
            size--;
            return removed;
        }
    }

    /**
     * Returns the data at the specified index.
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        } else if (index == 0) {
            return head.getData();
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (size == 0) {
            throw new NoSuchElementException("List is empty!");
        } else {
            CircularSinglyLinkedListNode<T> temp = null;
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < size - 1; i++) {
                if (curr.getNext().getData().equals(data)) {
                    temp = curr;
                }
                curr = curr.getNext();
            }
            if (temp == null) {
                if (head.getData().equals(data)) {
                    return removeFromFront();
                } else {
                    throw new NoSuchElementException("Data is not found!");
                }
            } else {
                T removed = temp.getNext().getData();
                temp.setNext(temp.getNext().getNext());
                size--;
                return removed;
            }
        }
    }


    /**
     * Returns an array representation of the linked list.
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] linkedListArray = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            linkedListArray[i] = curr.getData();
            curr = curr.getNext();
        }
        return linkedListArray;
    }

    /**
     * Returns the head node of the list.
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        return head;
    }

    /**
     * Returns the size of the list.
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}