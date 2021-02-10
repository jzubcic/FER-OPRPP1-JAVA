package hr.fer.oprpp1.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
    public void testBTH() {
        byte[] expected = {1, -82, 34};
        assertArrayEquals(expected, Util.hexToByte("01aE22"));
    }

    @Test
    public void testHTB() {
        byte[] actual = {1, -82, 34};
        String actualString = Util.byteToHex(actual);
        String expected = "01aE22";
        assertEquals(expected.toLowerCase(), actualString);
    }
}
