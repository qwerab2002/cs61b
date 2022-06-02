public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int first;

    private int last() {
        return (first + size - 1) % items.length;
    }

    private int moveFromFirst(int i) {
        return (items.length + first + i) % items.length;
    }

    private void resize(int capacity) {
        if (capacity < size) {
            return;
        }
        T[] a = (T[]) new Object[capacity];
        if (first <= last()) {
            System.arraycopy(items, first, a, 0, size);
            first = 0;
        } else {
            System.arraycopy(items, 0, a, items.length - first, last() + 1);
            System.arraycopy(items, first, a, 0, items.length - first);
            first = 0;
        }
        items = a;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            this.resize(size * 2);
        }
        first = moveFromFirst(-1);
        items[first] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            this.resize(size * 2);
        }
        size++;
        items[last()] = item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = first; i < first + size - 1; i++) {
            System.out.print(items[i % items.length]);
            System.out.print(" ");
        }
        System.out.println(items[last()]);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T itm = items[first];
        items[first] = null;
        first = moveFromFirst(1);
        size--;

        if (items.length >= 16) {
            while (size < items.length / 4) {
                this.resize(items.length / 2);
            }
        }

        return itm;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T itm = items[last()];
        items[last()] = null;
        size--;

        if (items.length >= 16) {
            while (size < items.length / 4) {
                this.resize(items.length / 2);
            }
        }

        return itm;
    }

    public T get(int index) {
        int idx = moveFromFirst(index);
        return items[idx];
    }

}
