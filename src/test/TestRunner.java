package test;

/**
 * Clase principal para ejecutar todos los tests del proyecto.
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("       SUITE DE TESTS - PROYECTO MINESWEEPER         ");
        System.out.println("           Persistencia y Tests         ");
        System.out.println("=======================================================");
        
        // Ejecutar tests de Board
        SimpleBoardTest boardTest = new SimpleBoardTest();
        boardTest.runAllTests();
        
        // Ejecutar tests de GameController y excepciones
        GameControllerTest controllerTest = new GameControllerTest();
        controllerTest.runAllTests();
        
        System.out.println("\n=======================================================");
        System.out.println("              TESTS COMPLETADOS                       ");
        System.out.println("=======================================================");
        
        // Demo de funcionalidades implementadas
        System.out.println("\n=== DEMO DE FUNCIONALIDADES IMPLEMENTADAS ===");
        demoFunctionalities();
    }
    
    private static void demoFunctionalities() {
        try {
            System.out.println("\n1. Demo de Excepciones:");
            demoExceptions();
            
            System.out.println("\n2. Demo de Persistencia:");
            demoPersistence();
            
            System.out.println("\n3. Demo de Polimorfismo:");
            demoPolymorphism();
            
        } catch (Exception e) {
            System.out.println("Error en demo: " + e.getMessage());
        }
    }
    
    private static void demoExceptions() {
        try {
            controller.GameController controller = new controller.GameController();
            controller.startGame();
            
            System.out.println("   - Probando InvalidMoveException...");
            try {
                controller.revealCell(-1, 0); // Coordenadas inválidas
            } catch (exceptions.InvalidMoveException e) {
                System.out.println("     InvalidMoveException capturada: " + e.getMessage());
            }
            
            System.out.println("   - Probando GameAlreadyOverException...");
            // Buscar una mina para terminar el juego
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    try {
                        boolean result = controller.revealCell(row, col);
                        if (!result) { // Encontró mina
                            try {
                                controller.toggleFlag(0, 0); // Intentar acción en juego terminado
                            } catch (exceptions.GameAlreadyOverException e) {
                                System.out.println("     GameAlreadyOverException capturada: " + e.getMessage());
                                return;
                            }
                        }
                    } catch (Exception ex) {
                        // Continuar buscando
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("     Error en demo de excepciones: " + e.getMessage());
        }
    }
    
    private static void demoPersistence() {
        try {
            controller.GameController controller = new controller.GameController();
            controller.startGame();
            controller.setPlayerName("Demo Player");
            
            System.out.println("   - Guardando juego...");
            controller.saveGame("demo_game");
            System.out.println("     Juego guardado como 'demo_game'");
            
            System.out.println("   - Listando juegos guardados...");
            String[] savedGames = controller.listSavedGames();
            System.out.println("     Juegos encontrados: " + savedGames.length);
            for (String game : savedGames) {
                System.out.println("       - " + game);
            }
            
            System.out.println("   - Cargando juego...");
            controller.loadGame("demo_game");
            System.out.println("     Juego cargado correctamente");
            
            System.out.println("   - Eliminando juego de prueba...");
            boolean deleted = controller.deleteGame("demo_game");
            System.out.println("     Juego eliminado: " + deleted);
            
        } catch (Exception e) {
            System.out.println("     Error en demo de persistencia: " + e.getMessage());
        }
    }
    
    private static void demoPolymorphism() {
        try {
            model.Board board = new model.Board();
            
            System.out.println("   - Demostrando polimorfismo Cell/MineCell/EmptyCell...");
            
            int mineCells = 0;
            int emptyCells = 0;
            
            for (int row = 0; row < 3; row++) { // Solo mostrar algunos ejemplos
                for (int col = 0; col < 3; col++) {
                    model.Cell cell = board.getCell(row, col);
                    
                    if (cell instanceof model.MineCell) {
                        mineCells++;
                        System.out.println("     Celda (" + row + "," + col + ") es MineCell - isMine(): " + cell.isMine());
                    } else if (cell instanceof model.EmptyCell) {
                        emptyCells++;
                        System.out.println("     Celda (" + row + "," + col + ") es EmptyCell - isMine(): " + cell.isMine());
                    }
                }
            }
            
            System.out.println("     En muestra 3x3: " + mineCells + " minas, " + emptyCells + " casillas vacías");
            System.out.println("     Polimorfismo funcionando correctamente");
            
        } catch (Exception e) {
            System.out.println("     Error en demo de polimorfismo: " + e.getMessage());
        }
    }
}
