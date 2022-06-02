
public class LinkedListDeque<T> {
    private TNode sentinel;
    private int size;

    public class TNode {
        private T item;
        private TNode next;
        private TNode prev;
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new TNode();
        sentinel.item = null;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item) {
        TNode first = new TNode();
        first.item = item;
        first.next = sentinel.next;
        first.prev = sentinel;
        sentinel.next.prev = first;
        sentinel.next = first;
        size ++;
    }

    public void addLast(T item){
        TNode last = new TNode();
        last.item = item;
        last.next = sentinel;
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size ++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        TNode p = sentinel.next;
        while (p.next != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println(p.item);
    }

    public T removeFirst() {
        if (sentinel.next != sentinel) {
            T itm = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size --;
            return itm;
        }
        return null;
    }

    public T removeLast() {
        if (sentinel.prev != sentinel) {
            T itm = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size --;
            return itm;
        }
        return null;
    }

    public T get(int index) {
        if (index >= size) return null;
        int n = 0;
        TNode p = sentinel.next;
        while (n < index) {
            p = p.next;
            n ++;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        return recursiveGetter(index, sentinel.next);
    }

    private T recursiveGetter(int index, TNode start) {
        if (index >= size) return null;
        if (index == 0) return start.item;
        return recursiveGetter(index - 1, start.next);
    }

}
