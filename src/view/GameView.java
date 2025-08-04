package view;

import controller.GameController;
import exceptions.*;
import model.Board;
import java.util.Scanner;

public class GameView {
    
    //Controllerpermite manejar la logica y el estado en el que se encuentra el juego
    private GameController controller;
    private Scanner scanner;

    //Constructor para leer por consola
    public GameView() {
        controller = new GameController();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("--------------------- BIENVENIDO AL BUSCAMINAS ---------------------");

        // Pedir nombre del jugador
        System.out.print("Ingrese nombre del jugador: ");
        String playerName = scanner.nextLine();
        controller.setPlayerName(playerName);  

        // Mostrar opciones al jugador con validación
        String option = getMenuOptionFromUser();
        
        if ("2".equals(option)) {
            if (loadGameFromMenu()) {
                System.out.println("¡Juego cargado exitosamente! Continuando partida...");
            } else {
                System.out.println("No se pudo cargar el juego. Iniciando nueva partida...");
                initializeNewGame();
            }
        } else {
            initializeNewGame();
        }

        //Se repite mientras el juego no termine
        while (!controller.isGameOver()) {
            controller.displayBoard(false);
            showGameMenu();
            String action = scanner.nextLine().toLowerCase();

            // Verificar acciones de menú primero
            if ("g".equals(action)) {
                saveGameFromMenu();
                continue;
            } else if ("c".equals(action)) {
                if (loadGameFromMenu()) {
                    System.out.println("¡Juego cargado exitosamente!");
                    continue;
                } else {
                    System.out.println("No se pudo cargar el juego.");
                    continue;
                }
            } else if ("l".equals(action)) {
                showSavedGames();
                continue;
            } else if ("s".equals(action)) {
                System.out.println("¡Hasta luego!");
                saveGameFromMenu();
                return;
            }

            // Acciones de juego normales
            if (!"d".equals(action) && !"m".equals(action)) {
                System.out.println(" Acción inválida. Use las opciones del menú:");
                System.out.println("    'd' para descubrir casilla");
                System.out.println("    'm' para marcar/desmarcar bandera");
                System.out.println("    'g' para guardar, 'c' para cargar, 'l' para listar, 's' para salir");
                continue;
            }

            System.out.println("Ingrese coordenadas (Ejm: A5):");
            String coord = scanner.nextLine().trim().toUpperCase();

            // Validar coordenadas usando el nuevo método
            int[] coordinates = validateCoordinates(coord);
            if (coordinates == null) {
                continue; // Error ya mostrado en validateCoordinates
            }
            
            int row = coordinates[0];
            int col = coordinates[1];

            //Comprueba si la accion es valida
            boolean validMove = false;

            try {
                if ("d".equals(action)) {
                    validMove = controller.revealCell(row, col);
                    if (!validMove) {
                        System.out.println("¡Oh no! Has descubierto una mina.");
                        System.out.println("El juego ha terminado.");
                    }
                } else if ("m".equals(action)) {
                    controller.toggleFlag(row, col);
                    validMove = true;
                } else {
                    System.out.println("Acción inválida. Intenta 'd' o 'm'.");
                    continue;
                }
            } catch (InvalidMoveException e) {
                System.out.println("Movimiento inválido: " + e.getMessage());
                continue;
            } catch (GameAlreadyOverException e) {
                System.out.println("El juego ya ha terminado: " + e.getMessage());
                break;
            }

            if (!validMove) {
                break;
            }
        }

        //Muestra l tablero completo con las minas
        controller.displayBoard(true);
        
        //Llama al controlador para imprimir al mensaje final
        controller.showResults();

        //Se cierra el scanner
        scanner.close();
    }
    
    /**
     * Inicializa un nuevo juego
     */
    private void initializeNewGame() {
        // Guardar el nombre del jugador actual
        String currentPlayerName = controller.getPlayerName();
        
        int numMines = getMineCountFromUser();
        try {
            controller = new GameController(numMines);
            controller.setPlayerName(currentPlayerName); // Restaurar el nombre del jugador
            controller.startGame();
            System.out.println("¡Tablero 10x10 con " + numMines + " minas creado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear el tablero: " + e.getMessage());
            // Volver a intentar con configuración por defecto
            controller = new GameController(); // Constructor por defecto
            controller.setPlayerName(currentPlayerName);
            controller.startGame();
        }
    }
    
    /**
     * Muestra el menú de opciones durante el juego
     */
    private void showGameMenu() {
        System.out.println("\n=== OPCIONES DE JUEGO ===");
        System.out.println("(d) Descubrir casilla");
        System.out.println("(m) Marcar/Desmarcar bandera");
        System.out.println("(g) Guardar juego");
        System.out.println("(c) Cargar juego");
        System.out.println("(l) Listar juegos guardados");
        System.out.println("(s) Guardar y salir");
        System.out.print("Elija una opción: ");
    }
    
    /**
     * Guarda el juego actual
     */
    private void saveGameFromMenu() {
        try {
            System.out.print("Ingrese nombre para guardar la partida: ");
            String filename = scanner.nextLine();
            
            if (filename.trim().isEmpty()) {
                filename = "partida_" + System.currentTimeMillis();
            }
            
            controller.saveGame(filename);
            System.out.println("Juego guardado como '" + filename + "'");
            
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
    
    /**
     * Carga un juego desde archivo
     * @return true si se cargó exitosamente, false en caso contrario
     */
    private boolean loadGameFromMenu() {
        try {
            String[] savedGames = controller.listSavedGames();
            
            if (savedGames.length == 0) {
                System.out.println("No hay juegos guardados.");
                return false;
            }
            
            System.out.println("\n=== JUEGOS GUARDADOS ===");
            for (int i = 0; i < savedGames.length; i++) {
                System.out.println((i + 1) + ". " + savedGames[i]);
            }
            
            System.out.print("Ingrese el número del juego a cargar (0 para cancelar): ");
            String input = scanner.nextLine();
            
            try {
                int choice = Integer.parseInt(input);
                
                if (choice == 0) {
                    return false;
                }
                
                if (choice < 1 || choice > savedGames.length) {
                    System.out.println("Opción inválida.");
                    return false;
                }
                
                controller.loadGame(savedGames[choice - 1]);
                return true;
                
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Muestra la lista de juegos guardados
     */
    private void showSavedGames() {
        String[] savedGames = controller.listSavedGames();
        
        if (savedGames.length == 0) {
            System.out.println("No hay juegos guardados.");
            return;
        }
        
        System.out.println("\n=== JUEGOS GUARDADOS ===");
        for (int i = 0; i < savedGames.length; i++) {
            System.out.println((i + 1) + ". " + savedGames[i]);
        }
        
        System.out.print("\n¿Desea eliminar algún juego? (s/n): ");
        String response = scanner.nextLine().toLowerCase();
        
        if ("s".equals(response)) {
            System.out.print("Ingrese el número del juego a eliminar: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                if (choice >= 1 && choice <= savedGames.length) {
                    if (controller.deleteGame(savedGames[choice - 1])) {
                        System.out.println("Juego eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el juego.");
                    }
                } else {
                    System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }
    
    /**
     * Solicita al usuario una opción del menú principal con validación
     * @return opción válida ("1" o "2")
     */
    private String getMenuOptionFromUser() {
        while (true) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Nuevo juego");
            System.out.println("2. Cargar juego guardado");
            System.out.print("Elija una opción (1-2): ");
            
            String input = scanner.nextLine().trim();
            
            // Validar que no esté vacío
            if (input.isEmpty()) {
                System.out.println("Error: Debe ingresar una opción.");
                continue;
            }
            
            // Validar opciones válidas
            if ("1".equals(input) || "2".equals(input)) {
                return input;
            } else {
                System.out.println("Error: '" + input + "' no es una opción válida. " +
                                 "Por favor ingrese 1 o 2.");
            }
        }
    }
    
    /**
     * Solicita al usuario el número de minas con validación
     * @return número de minas válido (entre 10 y 15)
     */
    private int getMineCountFromUser() {
        while (true) {
            System.out.println("\n=== CONFIGURACIÓN DEL JUEGO ===");
            System.out.println("Ingrese el número de minas (" + Board.getValidMineRange() + "):");
            System.out.print("Número de minas: ");
            
            String input = scanner.nextLine().trim();
            
            // Validar que no esté vacío
            if (input.isEmpty()) {
                System.out.println("Error: Debe ingresar un número.");
                continue;
            }
            
            try {
                int numMines = Integer.parseInt(input);
                
                if (Board.isValidMineCount(numMines)) {
                    return numMines;
                } else {
                    System.out.println("Error: El número de minas debe estar entre " + 
                                     Board.getValidMineRange() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + input + "' no es un número válido. " +
                                 "Por favor ingrese solo números.");
            }
        }
    }
    
    /**
     * Valida las coordenadas ingresadas por el usuario
     * @param coord coordenadas como string (ej: "A5")
     * @return array con [fila, columna] si es válido, null si no
     */
    private int[] validateCoordinates(String coord) {
        // Validar longitud mínima
        if (coord.length() < 2) {
            System.out.println("Error: Las coordenadas deben tener al menos 2 caracteres (ej: A5).");
            return null;
        }
        
        // Validar que el primer carácter sea una letra
        char rowChar = coord.charAt(0);
        if (!Character.isLetter(rowChar)) {
            System.out.println("Error: El primer carácter debe ser una letra (A-J).");
            return null;
        }
        
        // Convertir letra a índice numérico
        int row = Character.toUpperCase(rowChar) - 'A';
        
        // Validar que la fila esté en rango (0-9 para tablero 10x10)
        if (row < 0 || row >= 10) {
            System.out.println("Error: La fila debe ser una letra entre A y J.");
            return null;
        }
        
        // Validar la columna
        try {
            String colStr = coord.substring(1);
            int col = Integer.parseInt(colStr) - 1; // convertir a índice base 0
            
            // Validar que la columna esté en rango (0-9 para tablero 10x10)
            if (col < 0 || col >= 10) {
                System.out.println("Error: La columna debe ser un número entre 1 y 10.");
                return null;
            }
            
            return new int[]{row, col};
            
        } catch (NumberFormatException e) {
            System.out.println("Error: '" + coord.substring(1) + 
                             "' no es un número válido para la columna.");
            return null;
        }
    }
}
