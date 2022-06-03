import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void testSame() {
        int a = 0;
        int b = 0;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, b + 1));

        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(128, 128));
        assertTrue(Flik.isSameNumber(200, 200));
    }

}
