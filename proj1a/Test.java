public class Test {
    public static void main(String[] args) {
        ArrayDeque<Integer> lld = new ArrayDeque<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addLast(3);
        lld.addFirst(6);
        lld.addLast(99);
        for (int i = 0; i < 20; i++) {
            lld.addLast(i);
            lld.printDeque();
        }
        lld.addLast(1);
        lld.removeFirst();
        lld.removeLast();
        System.out.println(lld.size());
        for (int i = 0; i < 20; i++) {
            lld.removeFirst();
            lld.printDeque();

        }
    }
}
