package test;

import controller.GameController;

/**
 * Demo específico para mostrar funcionalidad de guardado de datos.
 */
public class PersistenceDemo {
    
    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("       DEMO DE PERSISTENCIA - GUARDADO DE DATOS       ");
        System.out.println("=======================================================");
        
        try {
            // Crear y configurar un juego
            GameController controller = new GameController();
            controller.setPlayerName("Jugador Demo");
            controller.startGame();
            
            System.out.println("1. CREANDO JUEGO INICIAL:");
            System.out.println("   - Jugador: Jugador Demo");
            System.out.println("   - Tablero 10x10 inicializado");
            System.out.println("   - 10 minas colocadas aleatoriamente");
            
            // Hacer algunos movimientos para cambiar el estado
            System.out.println("\n2. REALIZANDO ALGUNOS MOVIMIENTOS:");
            try {
                // Revelar algunas casillas
                boolean result1 = controller.revealCell(0, 0);
                System.out.println("   - Revelando casilla A1: " + (result1 ? "Segura" : "Mina"));
                
                boolean result2 = controller.revealCell(1, 1);
                System.out.println("   - Revelando casilla B2: " + (result2 ? "Segura" : "Mina"));
                
                // Marcar una bandera
                controller.toggleFlag(9, 9);
                System.out.println("   - Marcando bandera en J10");
                
            } catch (Exception e) {
                System.out.println("   - Movimiento: " + e.getMessage());
            }
            
            // Mostrar estado actual del tablero
            System.out.println("\n3. ESTADO ACTUAL DEL TABLERO:");
            controller.displayBoard(false);
            
            // Guardar el juego
            System.out.println("4. GUARDANDO JUEGO:");
            controller.saveGame("partida_demo");
            System.out.println("   Juego guardado como 'partida_demo.minesweeper'");
            
            controller.saveGame("partida_backup");
            System.out.println("   Backup guardado como 'partida_backup.minesweeper'");
            
            // Listar juegos guardados
            System.out.println("\n5. JUEGOS GUARDADOS:");
            String[] savedGames = controller.listSavedGames();
            System.out.println("   - Total de partidas guardadas: " + savedGames.length);
            for (int i = 0; i < savedGames.length; i++) {
                System.out.println("   " + (i + 1) + ". " + savedGames[i]);
            }
            
            // Crear un nuevo controller y cargar el juego
            System.out.println("\n6. SIMULANDO REINICIO DEL PROGRAMA:");
            GameController newController = new GameController();
            System.out.println("   - Nuevo controlador creado (tablero vacío)");
            
            System.out.println("\n7. CARGANDO PARTIDA GUARDADA:");
            newController.loadGame("partida_demo");
            System.out.println("   Partida 'partida_demo' cargada exitosamente");
            
            System.out.println("\n8. VERIFICANDO QUE EL ESTADO SE RECUPERÓ:");
            newController.displayBoard(false);
            
            // Verificar que se puede continuar jugando
            System.out.println("9. CONTINUANDO EL JUEGO CARGADO:");
            try {
                boolean result = newController.revealCell(2, 2);
                System.out.println("   - Nuevo movimiento en C3: " + (result ? "Segura" : "Mina"));
            } catch (Exception e) {
                System.out.println("   - Movimiento: " + e.getMessage());
            }
            
            // Mostrar detalles técnicos de la serialización
            System.out.println("\n10. DETALLES TÉCNICOS DE LA PERSISTENCIA:");
            System.out.println("    - Formato: Serialización de objetos Java");
            System.out.println("    - Extensión: .minesweeper");
            System.out.println("    - Ubicación: carpeta saves/");
            System.out.println("    - Contenido serializado:");
            System.out.println("      * Tablero completo (Board)");
            System.out.println("      * Estado de todas las casillas (Cell[])");
            System.out.println("      * Posiciones de minas");
            System.out.println("      * Casillas reveladas");
            System.out.println("      * Banderas marcadas");
            System.out.println("      * Estado del juego (GameState)");
            
            System.out.println("\n=======================================================");
            System.out.println("           DEMO DE PERSISTENCIA COMPLETADO            ");
            System.out.println("=======================================================");
            
        } catch (Exception e) {
            System.out.println("Error en demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
