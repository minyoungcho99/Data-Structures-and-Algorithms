public class CircularSinglyLinkedListNode<T> {

    private T data;
    private CircularSinglyLinkedListNode<T> next;

    CircularSinglyLinkedListNode(T data,
                                 CircularSinglyLinkedListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    CircularSinglyLinkedListNode(T data) {
        this(data, null);
    }

    T getData() {
        return data;
    }

    CircularSinglyLinkedListNode<T> getNext() {
        return next;
    }

    void setData(T data) {
        this.data = data;
    }

    void setNext(CircularSinglyLinkedListNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}
