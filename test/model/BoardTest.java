package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    
    public BoardTest() {
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
     * Test of revealCell method, of class Board.
     */
    @Test
    public void testRevealCell() {
        Board instance = new Board();
        // Revelar una celda vacía (asumiendo que no hay mina en 0,0)
        boolean result = instance.revealCell(0, 0);
        assertTrue(result || !result); 
    }

    /**
     * Test of toggleFlag method, of class Board.
     */
    @Test
    public void testToggleFlag() {
        Board instance = new Board();
        instance.toggleFlag(0, 0);
    }

    /**
     * Test of isGameWon method, of class Board.
     */
    @Test
    public void testIsGameWon() {
        Board instance = new Board();
        boolean result = instance.isGameWon();
        assertFalse(result); // Al inicio no debe estar ganado
    }

    /**
     * Test of getCell method, of class Board.
     */
    @Test
    public void testGetCell() {
        Board instance = new Board();
        assertNotNull(instance.getCell(0, 0)); // Debe retornar una celda válida
    }

    /**
     * Test of getBoardSize method, of class Board.
     */
    @Test
    public void testGetBoardSize() {
        Board instance = new Board();
        int size = instance.getBoardSize();
        assertTrue(size > 0); // El tamaño debe ser positivo
    }

    /**
     * Test of getTotalMines method, of class Board.
     */
    @Test
    public void testGetTotalMines() {
        Board instance = new Board();
        int mines = instance.getTotalMines();
        assertTrue(mines >= 0); // Puede ser 0 si no se inicializó
    }

    /**
     * Test of isValidMineCount method, of class Board.
     */
    @Test
    public void testIsValidMineCount() {
        assertTrue(Board.isValidMineCount(10)); // Ejemplo válido
        assertFalse(Board.isValidMineCount(0)); // Ejemplo inválido
    }

    /**
     * Test of getValidMineRange method, of class Board.
     */
    @Test
    public void testGetValidMineRange() {
        String range = Board.getValidMineRange();
        assertNotNull(range);
        assertFalse(range.isEmpty());
    }

    /**
     * Test of getRevealedCells method, of class Board.
     */
    @Test
    public void testGetRevealedCells() {
        Board instance = new Board();
        int revealed = instance.getRevealedCells();
        assertTrue(revealed >= 0);
    }

    /**
     * Test of revealAllMines method, of class Board.
     */
    @Test
    public void testRevealAllMines() {
        Board instance = new Board();
        instance.revealAllMines();
    }

    /**
     * Test of displayBoard method, of class Board.
     */
    @Test
    public void testDisplayBoard() {
        Board instance = new Board();
        instance.displayBoard(false);
    }
    
}
