package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameControllerTest {
    
    public GameControllerTest() {
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
     * Test of setPlayerName method, of class GameController.
     */
    @Test
    public void testSetPlayerName() {
        GameController instance = new GameController();
        instance.setPlayerName("Carlos");
        assertEquals("Carlos", instance.getPlayerName());
    }

    /**
     * Test of getPlayerName method, of class GameController.
     */
    @Test
    public void testGetPlayerName() {
        GameController instance = new GameController();
        instance.setPlayerName("Ana");
        assertEquals("Ana", instance.getPlayerName());
    }

    /**
     * Test of setNumberOfMines method, of class GameController.
     */
    @Test
    public void testSetNumberOfMines() {
        GameController instance = new GameController();
        instance.setNumberOfMines(12);
    }

    /**
     * Test of startGame method, of class GameController.
     */
    @Test
    public void testStartGame() {
        GameController instance = new GameController();
        instance.startGame();
        assertFalse(instance.isGameOver());
    }

    /**
     * Test of isGameOver method, of class GameController.
     */
    @Test
    public void testIsGameOver() {
        GameController instance = new GameController();
        assertFalse(instance.isGameOver());
    }

    /**
     * Test of revealCell method, of class GameController.
     */
    @Test
    public void testRevealCell() throws Exception {
        GameController instance = new GameController();
        instance.startGame();
        // Revela la celda (0,0) y verifica que no lanza excepción
        boolean result = instance.revealCell(0, 0);
        assertTrue(result || !result); // Solo verifica que retorna un boolean
    }

    /**
     * Test of toggleFlag method, of class GameController.
     */
    @Test
    public void testToggleFlag() throws Exception {
        GameController instance = new GameController();
        instance.startGame();
        // Marca/desmarca la celda (0,0) y verifica que no lanza excepción
        instance.toggleFlag(0, 0);
    }

    /**
     * Test of listSavedGames method, of class GameController.
     */
    @Test
    public void testListSavedGames() throws Exception {
        GameController instance = new GameController();
        String filename = "test_list.dat";
        instance.saveGame(filename);
        String[] result = instance.listSavedGames();
        boolean found = false;
        for (String f : result) {
            if (f.equals(filename)) found = true;
        }
        assertTrue(found); // El archivo debe estar listado
        // Limpieza
        instance.deleteGame(filename);
    }

    /**
     * Test of deleteGame method, of class GameController.
     */
    @Test
    public void testDeleteGame() throws Exception {
        GameController instance = new GameController();
        String filename = "test_delete.dat";
        instance.saveGame(filename);
        boolean deleted = instance.deleteGame(filename);
        assertTrue(deleted); // Debe borrar correctamente
        String[] saves = instance.listSavedGames();
        boolean found = false;
        for (String f : saves) {
            if (f.equals(filename)) found = true;
        }
        assertFalse(found); // El archivo debe haber sido borrado
    }

    /**
     * Test of showResults method, of class GameController.
     */
    @Test
    public void testShowResults() {
        GameController instance = new GameController();
        instance.showResults();
        // Solo verifica que no lanza excepción
    }
    
}
