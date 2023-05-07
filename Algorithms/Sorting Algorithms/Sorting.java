import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;

public class Sorting {

    /**
     * Implements selection sort.
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null!");
        }
        for (int i = 0; i < arr.length; i++) {
            int curr = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[curr], arr[j]) > 0) {
                    curr = j;
                }
            }
            swap(arr, i, curr);
        }

    }

    /**
     * Implements cocktail sort.
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null!");
        }
        int upperIdx = arr.length - 1;
        int lowerIdx = 0;
        int last = 0;
        boolean isSwapped = true;
        while (isSwapped) {
            isSwapped = false;
            for (int i = last; i < upperIdx; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    last = i;
                    isSwapped = true;
                }
            }
            upperIdx = last;
            if (!isSwapped) {
                return;
            }
            isSwapped = false;
            for (int j = upperIdx; j > lowerIdx; j--) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    swap(arr, j - 1, j);
                    last = j;
                    isSwapped = true;
                }
            }
            lowerIdx = last;
        }
    }

    /**
     * Implements merge sort.
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null!");
        }
        int length = arr.length;
        int midIdx = length / 2;
        int currIdx = 0;
        int leftIdx = 0;
        int rightIdx = 0;
        T[] leftArr = (T[]) new Object[midIdx];
        T[] rightArr = (T[]) new Object[length - midIdx];
        for (int i = 0; i < leftArr.length; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = 0; i < rightArr.length; i++) {
            rightArr[i] = arr[midIdx + i];
        }
        if (arr.length > 1) {
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
        }
        while (currIdx < arr.length) {
            if (leftIdx >= leftArr.length) {
                arr[currIdx] = rightArr[rightIdx];
                rightIdx++;
                currIdx++;
            } else if (rightIdx >= rightArr.length) {
                arr[currIdx] = leftArr[leftIdx];
                leftIdx++;
                currIdx++;
            } else if (comparator.compare(leftArr[leftIdx], rightArr[rightIdx]) <= 0) {
                arr[currIdx] = leftArr[leftIdx];
                leftIdx++;
                currIdx++;
            } else {
                arr[currIdx] = rightArr[rightIdx];
                rightIdx++;
                currIdx++;
            }
        }
    }

    /**
     * Implements kth select.
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Array, comparator, or the rand is null!");
        }
        return kthSelectHelper(k, arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Helps to find the kth smallest element in an array recursively.
     * @param k          the index to retrieve data from + 1
     * @param arr        array
     * @param <T>        data type to sort
     * @param comparator the Comparator used
     * @param rand       the Random object used
     * @param left       the left bound
     * @param right      the right bound
     * @return the kth smallest element
     */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator, Random rand, int left, int right) {
        if (left == right) {
            return arr[left];
        }

        int pivotIndex = rand.nextInt(right - left + 1) + left;
        pivotIndex = partition(arr, comparator, left, right, pivotIndex);

        if (k - 1 == pivotIndex) {
            return arr[pivotIndex];
        } else if (k - 1 < pivotIndex) {
            return kthSelectHelper(k, arr, comparator, rand, left, pivotIndex - 1);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, pivotIndex + 1, right);
        }
    }

    /**
     * Helps to partition the array.
     * @param arr        array
     * @param <T>        data type to sort
     * @param comparator the Comparator used
     * @param left       the left bound
     * @param right      the right boun
     * @param pivotIndex the index of the pivot
     * @return the new index of the pivot after partitioning
     */
    private static <T> int partition(T[] arr, Comparator<T> comparator, int left, int right, int pivotIndex) {
        T pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int newIndex = left;

        for (int i = left; i < right; i++) {
            if (comparator.compare(arr[i], pivotValue) < 0) {
                swap(arr, newIndex, i);
                newIndex++;
            }
        }

        swap(arr, right, newIndex);
        return newIndex;
    }

    /**
     * Implements LSD (least significant digit) radix sort.
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null!");
        }
        if (arr.length == 0) {
            return;
        }
        int div = 1;
        int kValue = k(arr);
        for (int i = 0; i < kValue; i++) {
            LinkedList<Integer>[] listArr = (LinkedList<Integer>[]) new LinkedList[19];
            for (int number: arr) {
                int idx1 = (number / div) % 10 + 9;
                if (listArr[idx1] == null) {
                    listArr[idx1] = new LinkedList<>();
                }
                listArr[idx1].addLast(number);
            }
            div *= 10;
            int idx2 = 0;
            for (LinkedList<Integer> list: listArr) {
                if (list != null) {
                    for (int j = 0; j < list.size(); j++) {
                        arr[idx2] = list.get(j);
                        idx2++;
                    }
                }
            }
        }
    }

    /**
     * Helps to find k operations for LSD radix sort.
     * @param arr array
     * @return length of the longest number
     */
    private static int k(int[] arr) {
        int k = 0;
        int s = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            int curr = Math.abs(arr[i]);
            if (curr == Integer.MIN_VALUE) {
                s = Integer.MIN_VALUE;
                break;
            } else if (curr > s) {
                s = curr;
            }
        }
        if (s == Integer.MIN_VALUE) {
            return 10;
        }
        if (s == 0) {
            k = 1;
        }
        while (s > 0) {
            s /= 10;
            k++;
        }
        return k;
    }

    /**
     * Implements heap sort.
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        PriorityQueue<Integer> minheap = new PriorityQueue<Integer>(data);
        int[] sort = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sort[i] = minheap.remove();
        }
        return sort;
    }

    /**
     * Helps to swap elements.
     * @param arr array
     * @param a index of a
     * @param b index of b
     * @param <T> element generic type of the array
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T data = arr[a];
        arr[a] = arr[b];
        arr[b] = data;
    }
}