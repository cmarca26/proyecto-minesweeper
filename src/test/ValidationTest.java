package test;

import model.Board;

/**
 * Clase para probar las nuevas validaciones implementadas
 */
public class ValidationTest {
    
    public static void main(String[] args) {
        System.out.println("=== TEST DE VALIDACIONES ===\n");
        
        testMineCountValidation();
        testBoardConstructors();
        testValidationMethods();
        
        System.out.println("\n=== TESTS COMPLETADOS ===");
    }
    
    /**
     * Prueba la validación del número de minas
     */
    private static void testMineCountValidation() {
        System.out.println("1. Probando validación de número de minas:");
        
        // Casos válidos
        testValidMineCount(10, true);  // Mínimo
        testValidMineCount(12, true);  // Intermedio
        testValidMineCount(15, true);  // Máximo
        
        // Casos inválidos
        testValidMineCount(5, false);   // Muy pocas
        testValidMineCount(9, false);   // Una menos del mínimo
        testValidMineCount(16, false);  // Una más del máximo
        testValidMineCount(20, false);  // Muchas
        testValidMineCount(-1, false);  // Negativo
        
        System.out.println();
    }
    
    /**
     * Prueba los constructores del Board
     */
    private static void testBoardConstructors() {
        System.out.println("2. Probando constructores del Board:");
        
        try {
            Board defaultBoard = new Board();
            System.out.println("Constructor por defecto: " + defaultBoard.getTotalMines() + " minas");
        } catch (Exception e) {
            System.out.println("Constructor por defecto falló: " + e.getMessage());
        }
        
        // Constructores válidos
        try {
            Board board10 = new Board(10);
            System.out.println("Constructor con 10 minas: " + board10.getTotalMines() + " minas");
        } catch (Exception e) {
            System.out.println("Constructor con 10 minas falló: " + e.getMessage());
        }
        
        try {
            Board board15 = new Board(15);
            System.out.println("Constructor con 15 minas: " + board15.getTotalMines() + " minas");
        } catch (Exception e) {
            System.out.println("Constructor con 15 minas falló: " + e.getMessage());
        }
        
        // Constructores inválidos
        try {
            Board boardInvalid = new Board(5);
            System.out.println("Constructor con 5 minas debería fallar pero no lo hizo");
        } catch (IllegalArgumentException e) {
            System.out.println("Constructor con 5 minas falló correctamente: " + e.getMessage());
        }
        
        try {
            Board boardInvalid = new Board(20);
            System.out.println("Constructor con 20 minas debería fallar pero no lo hizo");
        } catch (IllegalArgumentException e) {
            System.out.println("Constructor con 20 minas falló correctamente: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Prueba los métodos de validación estáticos
     */
    private static void testValidationMethods() {
        System.out.println("3. Probando métodos de validación:");
        
        System.out.println("Rango válido de minas: " + Board.getValidMineRange());
        System.out.println("MIN_MINES: " + Board.MIN_MINES);
        System.out.println("MAX_MINES: " + Board.MAX_MINES);
        
        System.out.println();
    }
    
    /**
     * Método auxiliar para probar validación de minas
     */
    private static void testValidMineCount(int mines, boolean shouldBeValid) {
        boolean isValid = Board.isValidMineCount(mines);
        String expected = shouldBeValid ? "válido" : "inválido";
        String result = (isValid ? "aceptado" : "rechazado");
        System.out.println(mines + " minas - " + expected + " (" + result + ")");
    }
}
