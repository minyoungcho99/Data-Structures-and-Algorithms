public class LinkedNode<T> {

    private T data;
    private LinkedNode<T> next;

    LinkedNode(T data, LinkedNode<T> next) {
        this.data = data;
        this.next = next;
    }
    
    LinkedNode(T data) {
        this(data, null);
    }
    
    T getData() {
        return data;
    }

    LinkedNode<T> getNext() {
        return next;
    }

    void setNext(LinkedNode<T> next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}
