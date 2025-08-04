package test;

import controller.GameController;
import exceptions.*;
import model.*;

/**
 * Clase de pruebas para GameController y manejo de excepciones.
 */
public class GameControllerTest {
    
    private GameController controller;
    private int testsPassed = 0;
    private int testsTotal = 0;
    
    public GameControllerTest() {
        controller = new GameController();
    }
    
    public void runAllTests() {
        System.out.println("\n=== INICIANDO TESTS DE GAME CONTROLLER ===");
        
        testControllerInitialization();
        testStartGame();
        testPlayerName();
        testInvalidMoveException();
        testGameAlreadyOverException();
        testPersistence();
        testExceptionMessages();
        
        System.out.println("\n=== RESUMEN DE TESTS DE CONTROLLER ===");
        System.out.println("Tests pasados: " + testsPassed + "/" + testsTotal);
        if (testsPassed == testsTotal) {
            System.out.println("¡TODOS LOS TESTS PASARON! ✓");
        } else {
            System.out.println("Algunos tests fallaron. ✗");
        }
    }
    
    private void testControllerInitialization() {
        testsTotal++;
        try {
            assertTrue(controller != null, "Controller no debe ser null");
            // Verificar que el controller está en estado inicial
            assertTrue(!controller.isGameOver(), "Juego no debe estar terminado al inicio");
            testsPassed++;
            System.out.println("testControllerInitialization");
        } catch (Exception e) {
            System.out.println("testControllerInitialization: " + e.getMessage());
        }
    }
    
    private void testStartGame() {
        testsTotal++;
        try {
            controller.startGame();
            // Verificar que se puede mostrar el tablero sin errores
            controller.displayBoard(false);
            testsPassed++;
            System.out.println("testStartGame");
        } catch (Exception e) {
            System.out.println("testStartGame: " + e.getMessage());
        }
    }
    
    private void testPlayerName() {
        testsTotal++;
        try {
            controller.setPlayerName("Jugador Test");
            // Si no hay excepción, el test pasa
            testsPassed++;
            System.out.println("testPlayerName");
        } catch (Exception e) {
            System.out.println("testPlayerName: " + e.getMessage());
        }
    }
    
    private void testInvalidMoveException() {
        testsTotal++;
        try {
            controller.startGame();
            
            // Test 1: Coordenadas inválidas
            try {
                controller.revealCell(-1, 0);
                System.out.println("testInvalidMoveException: Debería haber lanzado excepción para coordenadas inválidas");
                return;
            } catch (InvalidMoveException e) {
                assertTrue(e.getMessage().contains("inválidas"), "Mensaje debe mencionar coordenadas inválidas");
            }
            
            // Test 2: Revelar la misma celda dos veces
            // Primero encontrar una celda segura
            boolean foundSafeCell = false;
            for (int row = 0; row < 10 && !foundSafeCell; row++) {
                for (int col = 0; col < 10 && !foundSafeCell; col++) {
                    try {
                        boolean result = controller.revealCell(row, col);
                        if (result) { // Es una celda segura
                            try {
                                controller.revealCell(row, col); // Intentar revelar de nuevo
                                System.out.println("testInvalidMoveException: Debería haber lanzado excepción para celda ya revelada");
                                return;
                            } catch (InvalidMoveException e) {
                                assertTrue(e.getMessage().contains("revelada"), "Mensaje debe mencionar celda ya revelada");
                                foundSafeCell = true;
                            }
                        }
                    } catch (Exception ex) {
                        // Continuar buscando otra celda
                    }
                }
            }
            
            testsPassed++;
            System.out.println("testInvalidMoveException");
        } catch (Exception e) {
            System.out.println("testInvalidMoveException: " + e.getMessage());
        }
    }
    
    private void testGameAlreadyOverException() {
        testsTotal++;
        try {
            GameController testController = new GameController();
            testController.startGame();
            
            // Buscar una mina y revelarla para terminar el juego
            boolean gameEnded = false;
            for (int row = 0; row < 10 && !gameEnded; row++) {
                for (int col = 0; col < 10 && !gameEnded; col++) {
                    try {
                        boolean result = testController.revealCell(row, col);
                        if (!result) { // Encontró una mina
                            gameEnded = true;
                            
                            // Ahora intentar hacer un movimiento en un juego terminado
                            try {
                                testController.revealCell(0, 0);
                                System.out.println("testGameAlreadyOverException: Debería haber lanzado excepción para juego terminado");
                                return;
                            } catch (GameAlreadyOverException e) {
                                assertTrue(e.getMessage().contains("terminado"), "Mensaje debe mencionar juego terminado");
                            }
                        }
                    } catch (Exception ex) {
                        // Continuar buscando
                    }
                }
            }
            
            testsPassed++;
            System.out.println("testGameAlreadyOverException");
        } catch (Exception e) {
            System.out.println("testGameAlreadyOverException: " + e.getMessage());
        }
    }
    
    private void testPersistence() {
        testsTotal++;
        try {
            controller.startGame();
            
            // Test guardar juego
            try {
                controller.saveGame("test_game");
            } catch (Exception e) {
                System.out.println("testPersistence: Error al guardar - " + e.getMessage());
                return;
            }
            
            // Test listar juegos
            String[] savedGames = controller.listSavedGames();
            boolean found = false;
            for (String game : savedGames) {
                if (game.equals("test_game")) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "El juego guardado debe aparecer en la lista");
            
            // Test cargar juego
            try {
                controller.loadGame("test_game");
            } catch (Exception e) {
                System.out.println("testPersistence: Error al cargar - " + e.getMessage());
                return;
            }
            
            // Test eliminar juego
            boolean deleted = controller.deleteGame("test_game");
            assertTrue(deleted, "El juego debe eliminarse correctamente");
            
            testsPassed++;
            System.out.println("testPersistence");
        } catch (Exception e) {
            System.out.println("testPersistence: " + e.getMessage());
        }
    }
    
    private void testExceptionMessages() {
        testsTotal++;
        try {
            // Test InvalidMoveException
            InvalidMoveException invalidMove = new InvalidMoveException("Movimiento inválido");
            assertTrue(invalidMove.getMessage().equals("Movimiento inválido"), "Mensaje de InvalidMoveException incorrecto");
            
            InvalidMoveException invalidMoveWithCause = new InvalidMoveException("Mensaje", new RuntimeException("Causa"));
            assertTrue(invalidMoveWithCause.getMessage().equals("Mensaje"), "Mensaje con causa incorrecto");
            assertTrue(invalidMoveWithCause.getCause() != null, "Causa debe estar presente");
            
            // Test GameAlreadyOverException
            GameAlreadyOverException gameOver = new GameAlreadyOverException("Juego terminado");
            assertTrue(gameOver.getMessage().equals("Juego terminado"), "Mensaje de GameAlreadyOverException incorrecto");
            
            GameAlreadyOverException gameOverWithCause = new GameAlreadyOverException("Mensaje", new RuntimeException("Causa"));
            assertTrue(gameOverWithCause.getMessage().equals("Mensaje"), "Mensaje con causa incorrecto");
            assertTrue(gameOverWithCause.getCause() != null, "Causa debe estar presente");
            
            testsPassed++;
            System.out.println("testExceptionMessages");
        } catch (Exception e) {
            System.out.println("testExceptionMessages: " + e.getMessage());
        }
    }
    
    // Métodos de asserción simples
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    
    // Clase de excepción para aserciones
    private static class AssertionError extends RuntimeException {
        public AssertionError(String message) {
            super(message);
        }
    }
}
