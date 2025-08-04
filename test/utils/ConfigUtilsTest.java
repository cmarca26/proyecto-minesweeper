package utils;

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigUtilsTest {
    
    public ConfigUtilsTest() {
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

    @Test
    public void testGetMineCountFromUser() {
        Scanner scanner = new Scanner("12\n");
        int result = ConfigUtils.getMineCountFromUser(scanner);
        assertEquals(12, result);
    }

    @Test
    public void testGetMineCountFromUser_valid() {
        Scanner scanner = new Scanner("10\n");
        int result = ConfigUtils.getMineCountFromUser(scanner);
        assertEquals(10, result);
    }

    @Test
    public void testGetMineCountFromUser_invalid_then_valid() {
        Scanner scanner = new Scanner("abc\n5\n12\n");
        int result = ConfigUtils.getMineCountFromUser(scanner);
        assertEquals(12, result);
    }
}
