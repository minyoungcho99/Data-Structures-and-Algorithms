import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    public BST() {
    }

    /**
     * Constructs a new BST.
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            for (T d: data) {
                if (d == null) {
                    throw new IllegalArgumentException("Data is null!");
                } else {
                    add(d);
                }
            }
        }
    }

    /**
     * Adds the data to the tree.
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        BSTNode<T> newNode = new BSTNode<>(data);
        root = add(root, newNode);
    }

    /**
     * Helps to add the data to the tree recursively.
     * @param currNode current node
     * @param newNode new added node
     * @return node that is currently pointed
     */
    private BSTNode<T> add(BSTNode<T> currNode, BSTNode<T> newNode) {
        if (currNode == null) {
            size++;
            return newNode;
        } else if (newNode.getData().compareTo(currNode.getData()) > 0) {
            BSTNode<T> right = add(currNode.getRight(), newNode);
            currNode.setRight(right);
        } else if (newNode.getData().compareTo(currNode.getData()) < 0) {
            BSTNode<T> left = add(currNode.getLeft(), newNode);
            currNode.setLeft(left);
        }
        return currNode;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            BSTNode<T> removedContainer = new BSTNode<T>(null);
            root = remove(root, data, removedContainer);
            return removedContainer.getData();
        }
    }

    /**
     * Helps to remove the data from the tree recursively.
     * @param currNode current node
     * @param data successor's data
     * @param removedNode removed node
     * @return node that is currently pointed
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> remove(BSTNode<T> currNode, T data, BSTNode<T> removedNode) {
        if (currNode == null) {
            throw new NoSuchElementException("Data is not in the tree!");
        } else if (currNode.getData().compareTo(data) > 0) {
            BSTNode<T> left = remove(currNode.getLeft(), data, removedNode);
            currNode.setLeft(left);
        } else if (currNode.getData().compareTo(data) < 0) {
            BSTNode<T> right = remove(currNode.getRight(), data, removedNode);
            currNode.setRight(right);
        } else if (currNode.getData().compareTo(data) == 0) {
            removedNode.setData(currNode.getData());
            size--;
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            }
            else if (currNode.getLeft() != null && currNode.getRight() != null) {
                BSTNode<T> nullNode = new BSTNode<>(null);
                BSTNode<T> rNode
                        = replaceSuccessor(currNode.getRight(), nullNode);
                currNode.setRight(rNode);
                currNode.setData(nullNode.getData());
            }
            else {
                if (currNode.getLeft() != null) {
                    return currNode.getLeft();
                } else if (currNode.getRight() != null) {
                    return currNode.getRight();
                }
            }
        }
        return currNode;
    }

    /**
     * Helps to remove the data from the tree recursively.
     * @param currNode current node
     * @param successor successor node
     * @return the node to be pointed at
     */
    private BSTNode<T> replaceSuccessor(BSTNode<T> currNode, BSTNode<T> successor) {
        if (currNode.getLeft() == null) {
            successor.setData(currNode.getData());
            return currNode.getRight();
        } else {
            BSTNode<T> node = replaceSuccessor(currNode.getLeft(), successor);
            currNode.setLeft(node);
        }
        return currNode;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        BSTNode<T> result = get(root, data);
        return result.getData();

    }

    /**
     * Helps to get the data from the tree matching the given parameter recursively.
     * @param currNode current node
     * @param data the data in the tree equal to the parameter
     * @return the node with the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> get(BSTNode<T> currNode, T data) {
        if (currNode == null) {
            throw new NoSuchElementException("Data is not in the tree!");
        } else if (currNode.getData().compareTo(data) > 0) {
            return get(currNode.getLeft(), data);
        } else if (currNode.getData().compareTo(data) < 0) {
            return get(currNode.getRight(), data);
        } else {
            return currNode;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            return contains(root, data);
        }
    }

    /**
     * Helps to check whether or not data matching the given parameter
     * is contained within the tree recursively.
     * @param currNode current node
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean contains(BSTNode<T> currNode, T data) {
        if (currNode == null) {
            return false;
        } else if (currNode.getData().compareTo(data) > 0) {
            return contains(currNode.getLeft(), data);
        } else if (currNode.getData().compareTo(data) < 0) {
            return contains(currNode.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> arr = new ArrayList<>();
        preorder(root, arr);
        return arr;
    }

    /**
     * Helps to generate a pre-order traversal of the tree recursively.
     * @param currNode current node
     * @param arr list that are being used as storage
     */
    private void preorder(BSTNode<T> currNode, List<T> arr) {
        if (currNode != null) {
            arr.add(currNode.getData());
            preorder(currNode.getLeft(), arr);
            preorder(currNode.getRight(), arr);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> arr = new ArrayList<>();
        inorder(root, arr);
        return arr;
    }

    /**
     * Helps to generate an in-order traversal of the tree recursively.
     * @param currNode current node
     * @param arr list that are being used as storage
     */
    private void inorder(BSTNode<T> currNode, List<T> arr) {
        if (currNode != null) {
            inorder(currNode.getLeft(), arr);
            arr.add(currNode.getData());
            inorder(currNode.getRight(), arr);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> arr = new ArrayList<>();
        postorder(root, arr);
        return arr;
    }

    /**
     * Helps to generate a post-order traversal of the tree recursively.
     * @param currNode current node
     * @param arr list that are being used as storage
     */
    private void postorder(BSTNode<T> currNode, List<T> arr) {
        if (currNode != null) {
            postorder(currNode.getLeft(), arr);
            postorder(currNode.getRight(), arr);
            arr.add(currNode.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> arr = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root == null) {
            return arr;
        }
        queue.add(root);
        BSTNode<T> currNode;
        while (!queue.isEmpty()) {
            currNode = queue.remove();
            arr.add(currNode.getData());
            if (currNode.getLeft() != null) {
                queue.add(currNode.getLeft());
            }
            if (currNode.getRight() != null) {
                queue.add(currNode.getRight());
            }
        }
        return arr;
    }

    /**
     * Returns the height of the root of the tree.
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return height(root);
        }
    }

    /**
     * Helps to return the height of the root of the tree recursively.
     * @param currNode current node
     * @return the height of the subtree
     */
    private int height(BSTNode<T> currNode) {
        if (currNode == null) {
            return -1;
        } else {
            int leftH = height(currNode.getLeft());
            int rightH = height(currNode.getRight());
            if (leftH > rightH) {
                return leftH + 1;
            } else {
                return rightH + 1;
            }
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new IllegalArgumentException("k is less than zero or greater than the size of the tree!");
        }
        LinkedList<T> sortedList = new LinkedList<>();
        kLargest(root, sortedList, k);
        return sortedList;
    }

    /**
     * Helps to find and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest recursively.
     * @param currNode current node
     * @param result stack that stores the traversed data
     * @param k target size
     */
    private void kLargest(BSTNode<T> currNode, LinkedList<T> result, int k) {
        if (currNode != null && result.size() < k) {
            kLargest(currNode.getRight(), result, k);
            if (result.size() >= k) {
                return;
            }
            result.addFirst(currNode.getData());
            kLargest(currNode.getLeft(), result, k);
        }
    }

    /**
     * Returns the root of the tree.
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}