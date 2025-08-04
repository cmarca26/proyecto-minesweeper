package utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CoordinateUtilsTest {
    
    public CoordinateUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseCoordinates method, of class CoordinateUtils.
     */
    @Test
    public void testParseCoordinates_valid() {
        int[] result = CoordinateUtils.parseCoordinates("A1");
        assertArrayEquals(new int[]{0, 0}, result);

        result = CoordinateUtils.parseCoordinates("J10");
        assertArrayEquals(new int[]{9, 9}, result);
    }

    @Test
    public void testParseCoordinates_invalid() {
        assertNull(CoordinateUtils.parseCoordinates(""));
        assertNull(CoordinateUtils.parseCoordinates("Z5"));
        assertNull(CoordinateUtils.parseCoordinates("A0"));
        assertNull(CoordinateUtils.parseCoordinates("A11"));
        assertNull(CoordinateUtils.parseCoordinates("1A"));
        assertNull(CoordinateUtils.parseCoordinates(null));
    }
}
