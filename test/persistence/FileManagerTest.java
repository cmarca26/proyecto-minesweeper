package persistence;

import model.Board;
import model.GameState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileManagerTest {
    
    public FileManagerTest() {
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
     * Test of saveGame method, of class FileManager.
     */
    @Test
    public void testSaveGame() throws Exception {
        Board board = new Board();
        GameState gameState = GameState.IN_PROGRESS;
        String filename = "test_save.dat";
        FileManager.saveGame(board, gameState, filename);
        // Verifica que el archivo se haya guardado
        String[] saves = FileManager.listSavedGames();
        boolean found = false;
        for (String f : saves) {
            if (f.equals(filename)) found = true;
        }
        assertTrue(found);
        // Limpieza
        FileManager.deleteGame(filename);
    }

    /**
     * Test of loadGame method, of class FileManager.
     */
    @Test
    public void testLoadGame() throws Exception {
        Board board = new Board();
        GameState gameState = GameState.IN_PROGRESS;
        String filename = "test_load.dat";
        FileManager.saveGame(board, gameState, filename);
        GameData result = FileManager.loadGame(filename);
        assertNotNull(result);
        assertNotNull(result.getBoard());
        assertEquals(GameState.IN_PROGRESS, result.getGameState());
        // Limpieza
        FileManager.deleteGame(filename);
    }

    /**
     * Test of listSavedGames method, of class FileManager.
     */
    @Test
    public void testListSavedGames() {
        String filename = "test_list.dat";
        try {
            FileManager.saveGame(new Board(), GameState.IN_PROGRESS, filename);
        } catch (Exception e) {}
        String[] result = FileManager.listSavedGames();
        boolean found = false;
        for (String f : result) {
            if (f.equals(filename)) found = true;
        }
        assertTrue(found);
        // Limpieza
        FileManager.deleteGame(filename);
    }

    /**
     * Test of deleteGame method, of class FileManager.
     */
    @Test
    public void testDeleteGame() {
        String filename = "test_delete.dat";
        try {
            FileManager.saveGame(new Board(), GameState.IN_PROGRESS, filename);
        } catch (Exception e) {}
        boolean deleted = FileManager.deleteGame(filename);
        assertTrue(deleted);
        String[] saves = FileManager.listSavedGames();
        boolean found = false;
        for (String f : saves) {
            if (f.equals(filename)) found = true;
        }
        assertFalse(found);
    }
    
}
