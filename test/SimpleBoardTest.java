package test;

import model.*;

/**
 * Clase de pruebas para Board (sin JUnit).
 */
public class SimpleBoardTest {
    
    private Board board;
    private int testsPassed = 0;
    private int testsTotal = 0;
    
    public SimpleBoardTest() {
        board = new Board();
    }
    
    public void runAllTests() {
        System.out.println("=== INICIANDO TESTS DE BOARD ===");
        
        testBoardInitialization();
        testBoardSize();
        testCellsExist();
        testInvalidPositions();
        testMineCount();
        testInitialState();
        testGameNotWonInitially();
        testToggleFlag();
        testRevealSafeCell();
        testRevealMineCell();
        testPolymorphism();
        
        System.out.println("\n=== RESUMEN DE TESTS ===");
        System.out.println("Tests pasados: " + testsPassed + "/" + testsTotal);
        if (testsPassed == testsTotal) {
            System.out.println("¡TODOS LOS TESTS PASARON! ");
        } else {
            System.out.println("Algunos tests fallaron. ");
        }
    }
    
    private void testBoardInitialization() {
        testsTotal++;
        try {
            assertTrue(board != null, "Board no debe ser null");
            assertEquals(Board.BOARD_SIZE, board.getBoardSize(), "Tamaño de board incorrecto");
            assertEquals(10, board.getTotalMines(), "Número de minas incorrecto"); // Constructor por defecto usa 10 minas
            testsPassed++;
            System.out.println(" testBoardInitialization");
        } catch (Exception e) {
            System.out.println(" testBoardInitialization: " + e.getMessage());
        }
    }
    
    private void testBoardSize() {
        testsTotal++;
        try {
            assertEquals(10, Board.BOARD_SIZE, "BOARD_SIZE debe ser 10");
            assertEquals(10, Board.MIN_MINES, "MIN_MINES debe ser 10");
            assertEquals(15, Board.MAX_MINES, "MAX_MINES debe ser 15");
            testsPassed++;
            System.out.println(" testBoardSize");
        } catch (Exception e) {
            System.out.println(" testBoardSize: " + e.getMessage());
        }
    }
    
    private void testCellsExist() {
        testsTotal++;
        try {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    assertTrue(board.getCell(row, col) != null, 
                        "Celda (" + row + ", " + col + ") no debe ser null");
                }
            }
            testsPassed++;
            System.out.println(" testCellsExist");
        } catch (Exception e) {
            System.out.println(" testCellsExist: " + e.getMessage());
        }
    }
    
    private void testInvalidPositions() {
        testsTotal++;
        try {
            assertTrue(board.getCell(-1, 0) == null, "Posición (-1, 0) debe ser null");
            assertTrue(board.getCell(0, -1) == null, "Posición (0, -1) debe ser null");
            assertTrue(board.getCell(Board.BOARD_SIZE, 0) == null, "Posición fuera del límite debe ser null");
            assertTrue(board.getCell(0, Board.BOARD_SIZE) == null, "Posición fuera del límite debe ser null");
            testsPassed++;
            System.out.println(" testInvalidPositions");
        } catch (Exception e) {
            System.out.println(" testInvalidPositions: " + e.getMessage());
        }
    }
    
    private void testMineCount() {
        testsTotal++;
        try {
            int mineCount = 0;
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    if (board.getCell(row, col).isMine()) {
                        mineCount++;
                    }
                }
            }
            assertEquals(board.getTotalMines(), mineCount, "Número de minas incorrecto");
            testsPassed++;
            System.out.println(" testMineCount");
        } catch (Exception e) {
            System.out.println(" testMineCount: " + e.getMessage());
        }
    }
    
    private void testInitialState() {
        testsTotal++;
        try {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    Cell cell = board.getCell(row, col);
                    assertTrue(!cell.isRevealed(), "Celda (" + row + ", " + col + ") no debe estar revelada inicialmente");
                    assertTrue(!cell.isFlagged(), "Celda (" + row + ", " + col + ") no debe estar marcada inicialmente");
                }
            }
            testsPassed++;
            System.out.println(" testInitialState");
        } catch (Exception e) {
            System.out.println(" testInitialState: " + e.getMessage());
        }
    }
    
    private void testGameNotWonInitially() {
        testsTotal++;
        try {
            assertTrue(!board.isGameWon(), "El juego no debe estar ganado inicialmente");
            testsPassed++;
            System.out.println(" testGameNotWonInitially");
        } catch (Exception e) {
            System.out.println(" testGameNotWonInitially: " + e.getMessage());
        }
    }
    
    private void testToggleFlag() {
        testsTotal++;
        try {
            Cell cell = board.getCell(0, 0);
            assertTrue(!cell.isFlagged(), "Celda no debe estar marcada inicialmente");
            
            board.toggleFlag(0, 0);
            assertTrue(cell.isFlagged(), "Celda debe estar marcada después de toggle");
            
            board.toggleFlag(0, 0);
            assertTrue(!cell.isFlagged(), "Celda no debe estar marcada después del segundo toggle");
            testsPassed++;
            System.out.println(" testToggleFlag");
        } catch (Exception e) {
            System.out.println(" testToggleFlag: " + e.getMessage());
        }
    }
    
    private void testRevealSafeCell() {
        testsTotal++;
        try {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    if (!board.getCell(row, col).isMine()) {
                        boolean result = board.revealCell(row, col);
                        assertTrue(result, "Revelar celda segura debe retornar true");
                        assertTrue(board.getCell(row, col).isRevealed(), "Celda debe estar revelada");
                        testsPassed++;
                        System.out.println(" testRevealSafeCell");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" testRevealSafeCell: " + e.getMessage());
        }
    }
    
    private void testRevealMineCell() {
        testsTotal++;
        try {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    if (board.getCell(row, col).isMine()) {
                        Board newBoard = new Board(); // Nuevo board para no afectar otros tests
                        boolean result = newBoard.revealCell(row, col);
                        assertTrue(!result, "Revelar mina debe retornar false");
                        testsPassed++;
                        System.out.println(" testRevealMineCell");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" testRevealMineCell: " + e.getMessage());
        }
    }
    
    private void testPolymorphism() {
        testsTotal++;
        try {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    Cell cell = board.getCell(row, col);
                    
                    // Todas las casillas deben ser instancias de Cell
                    assertTrue(cell instanceof Cell, "Celda debe ser instancia de Cell");
                    
                    // Debe ser MineCell o EmptyCell
                    assertTrue(cell instanceof MineCell || cell instanceof EmptyCell, 
                        "Celda debe ser MineCell o EmptyCell");
                    
                    // Verificar que el comportamiento polimórfico es correcto
                    if (cell instanceof MineCell) {
                        assertTrue(cell.isMine(), "MineCell debe retornar true en isMine()");
                    } else {
                        assertTrue(!cell.isMine(), "EmptyCell debe retornar false en isMine()");
                    }
                }
            }
            testsPassed++;
            System.out.println(" testPolymorphism");
        } catch (Exception e) {
            System.out.println(" testPolymorphism: " + e.getMessage());
        }
    }
    
    // Métodos de asserción simples
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    
    private void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Esperado: " + expected + ", Actual: " + actual);
        }
    }
    
    // Clase de excepción para aserciones
    private static class AssertionError extends RuntimeException {
        public AssertionError(String message) {
            super(message);
        }
    }
}
