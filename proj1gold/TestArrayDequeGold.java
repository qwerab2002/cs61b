import org.junit.Test;

import static org.junit.Assert.*;

public class TestArrayDequeGold {

    @Test
    public void testSAD() {
        String log = "";
        StudentArrayDeque<Integer> actual = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();
        while (true) {
            int add = StdRandom.uniform(100);
            int opr = StdRandom.uniform(4);
            if (expected.size() < 1) {
                opr /= 2;
            }
            Integer actualRmv = 0;
            Integer expectedRmv = 0;

            switch (opr) {
                case 0:
                    actual.addFirst(add);
                    expected.addFirst(add);
                    log += "addFirst(" + add + ")\n";
                    break;
                case 1:
                    actual.addLast(add);
                    expected.addLast(add);
                    log += "addLast(" + add + ")\n";
                    break;
                case 2:
                    actualRmv = actual.removeFirst();
                    expectedRmv = expected.removeFirst();
                    log += "removeFirst()\n";
                    break;
                case 3:
                    actualRmv = actual.removeLast();
                    expectedRmv = expected.removeLast();
                    log += "removeLast()\n";
                    break;
                default:
            }

            assertEquals(log, expectedRmv, actualRmv);
        }
    }
}
