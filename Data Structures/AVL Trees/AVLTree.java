import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     */
    public AVL() {
    }

    /**
     * Constructs a new AVL.
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("Element in data is null!");
            } else {
                add(element);
            }
        }
    }

    /**
     * Adds the element to the tree.
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        root = add(root, data);
    }

    /**
     * Helps to add the element to the tree.
     * @param curr current node
     * @param data the data to add
     * @return added node
     */
    private AVLNode<T> add(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(add(curr.getRight(), data));
        }
        update(curr);
        return balance(curr);
    }

    /**
     * Helps to update balance factor and height of the node.
     * @param node current node
     */
    private void update(AVLNode<T> node) {
        int rHeight = -1;
        int lHeight = -1;
        if (node.getLeft() != null) {
            lHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rHeight = node.getRight().getHeight();
        }
        int height = 1 + Math.max(lHeight, rHeight);
        node.setHeight(height);
        node.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * Helps to left rotate.
     * @param curr current node
     * @return result node
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> newNode = curr.getRight();
        curr.setRight(newNode.getLeft());
        newNode.setLeft(curr);
        update(curr);
        update(newNode);
        return newNode;
    }

    /**
     * Helps to right rotate.
     * @param curr current node
     * @return result node
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> newNode = curr.getLeft();
        curr.setLeft(newNode.getRight());
        newNode.setRight(curr);
        update(curr);
        update(newNode);
        return newNode;
    }

    /**
     * Helps to balance.
     * @param curr current node
     * @return result node
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            } else {
                curr = leftRotate(curr);
            }
        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            } else {
                curr = rightRotate(curr);
            }
        }
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            AVLNode<T> node = new AVLNode<T>(null);
            root = remove(root, data, node);
            return node.getData();
        }
    }

    /**
     * Helps to remove and return the element from the tree matching the given
     * parameter.
     * @param curr current node
     * @param data data to remove
     * @param stored node to store data
     * @return result node
     */
    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> stored) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not found!");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, stored));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, stored));
        } else {
            stored.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else {
                AVLNode<T> temp = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), temp));
                curr.setData(temp.getData());
            }
        }
        update(curr);
        return balance(curr);
    }

    /**
     * Removes predecessor of the node in a recursive way.
     * @param curr current node
     * @param stored node to store data
     * @return removed node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> stored) {
        if (curr.getRight() == null) {
            stored.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), stored));
            update(curr);
            return balance(curr);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (get(root, data) == null) {
            throw new NoSuchElementException("Data is not in the tree!");
        }
        return get(root, data);
    }

    /**
     * Helps to return the element from the tree matching the given parameter.
     * @param curr current node
     * @param data data to search for in the tree
     * @return the data in the tree equal to the parameter
     */
    private T get(AVLNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else {
            T marked = curr.getData();
            if (marked.compareTo(data) < 0) {
                return get(curr.getRight(), data);
            } else if (marked.compareTo(data) > 0) {
                return get(curr.getLeft(), data);
            } else {
                return curr.getData();
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (size == 0) {
            return false;
        }
        return contains(root, data);
    }

    /**
     * Helps to return whether or not data matching the given parameter is contained
     * within the tree.
     * @param curr current node
     * @param data data to search for in the tree
     * @return true if the data is contained within the tree
     */
    private boolean contains(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) > 0) {
            return contains(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return contains(curr.getRight(), data);
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
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
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        if (root != null) {
            deepestBranches(root, list);
        }
        return list;
    }

    /**
     * Helps to return the data on branches of the tree with the maximum depth recursively.
     * @param curr current node
     * @param list result list
     */
    private void deepestBranches(AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        AVLNode<T> left = curr.getLeft();
        AVLNode<T> right = curr.getRight();
        list.add(curr.getData());
        if (left == null && right == null) {
            return;
        }
        if (left != null && right != null && left.getHeight() == right.getHeight()) {
            deepestBranches(left, list);
            deepestBranches(right, list);
        } else if ((left != null && right == null) || (left != null && right != null
                && left.getHeight() > right.getHeight())) {
            deepestBranches(left, list);
        } else if ((left == null && right != null) || (left != null && right != null
                && left.getHeight() < right.getHeight())) {
            deepestBranches(right, list);
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data1 or data2 are null!");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data is smaller than data2!");
        }
        List<T> list = new ArrayList<>();
        if (root != null) {
            sortedInBetween(root, data1, data2, list);
        }
        return list;
    }

    /**
     * helps to return a sorted list of data that are within the threshold bounds of
     * data1 and data2 recursively.
     * @param curr current node
     * @param data1 left bound
     * @param data2 right bound
     * @param list result list
     */
    private void sortedInBetween(AVLNode<T> curr, T data1, T data2, List<T> list) {
        if (curr == null) {
            return;
        }
        T data = curr.getData();
        if (data.compareTo(data1) > 0) {
            sortedInBetween(curr.getLeft(), data1, data2, list);
        }
        if (data.compareTo(data1) > 0 && data.compareTo(data2) < 0) {
            list.add(data);
        }
        if (data.compareTo(data2) < 0) {
            sortedInBetween(curr.getRight(), data1, data2, list);
        }
    }

    /**
     * Returns the root of the tree.
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     * @return the size of the tree
     */
    public int size() {

        return size;
    }