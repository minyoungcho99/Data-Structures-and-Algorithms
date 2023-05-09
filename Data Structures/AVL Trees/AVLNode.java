public class AVLNode<T extends Comparable<? super T>> {
    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    private int balanceFactor;

    public AVLNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }
    
    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getBalanceFactor() {
        return balanceFactor;
    }
    
    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    @Override
    public String toString() {
        return String.format("Node containing %s (height %d, bf %d)",
                data.toString(), height, balanceFactor);
    }
}
