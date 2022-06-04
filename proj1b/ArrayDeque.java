public class ArrayDeque<T> implements Deque<T> {
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

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            this.resize(size * 2);
        }
        first = moveFromFirst(-1);
        items[first] = item;
        size++;
    }
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            this.resize(size * 2);
        }
        size++;
        items[last()] = item;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        for (int i = first; i < first + size - 1; i++) {
            System.out.print(items[i % items.length]);
            System.out.print(" ");
        }
        System.out.println(items[last()]);
    }
    @Override
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
    @Override
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
    @Override
    public T get(int index) {
        int idx = moveFromFirst(index);
        return items[idx];
    }

}
