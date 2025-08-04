package view;

import controller.GameController;
import java.util.Scanner;
import exceptions.InvalidMoveException;
import exceptions.GameAlreadyOverException;
import utils.ConfigUtils;
import utils.CoordinateUtils;
import utils.MenuUtils;

public class GameView implements View {
    @Override
    public void showGameMenu() {
        MenuUtils.showGameMenu();
    }
    private GameController controller;
    private final Scanner scanner;

    // Permite inyectar dependencias para pruebas y reutilización
    public GameView(GameController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    // Constructor por defecto
    public GameView() {
        this(new GameController(), new Scanner(System.in));
    }

    @Override
    public void start() {
        System.out.println("--------------------- BIENVENIDO AL BUSCAMINAS ---------------------");
        System.out.print("Ingrese nombre del jugador: ");
        String playerName = scanner.nextLine();
        controller.setPlayerName(playerName);

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

        while (!controller.isGameOver()) {
            controller.displayBoard(false);
            // El menú ahora se muestra usando MenuUtils
            MenuUtils.showGameMenu(); 
            String action = scanner.nextLine().toLowerCase();

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

            if (!"d".equals(action) && !"m".equals(action)) {
                System.out.println("Accion invalida. Use las opciones del menu:");
                MenuUtils.showGameMenu();
                continue;
            }

            System.out.println("Ingrese coordenadas (Ejm: A5):");
            String coord = scanner.nextLine().trim().toUpperCase();
            int[] coordinates = CoordinateUtils.parseCoordinates(coord);
            if (coordinates == null) {
                System.out.println("Coordenadas invalidas. Ejemplo valido: A5");
                continue;
            }
            int row = coordinates[0];
            int col = coordinates[1];

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
                }
            } catch (InvalidMoveException e) {
                System.out.println("Movimiento invalido: " + e.getMessage());
                continue;
            } catch (GameAlreadyOverException e) {
                System.out.println("El juego ya ha terminado: " + e.getMessage());
                break;
            }
            if (!validMove) {
                break;
            }
        }
        controller.displayBoard(true);
        showResults();
        scanner.close();
    }

    @Override
    public void showResults() {
        controller.showResults();
    }

    /**
     * Inicializa un nuevo juego
     */
    private void initializeNewGame() {
        // Guardar el nombre del jugador actual
        String currentPlayerName = controller.getPlayerName();

        int numMines = ConfigUtils.getMineCountFromUser(scanner);
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
    // Método eliminado, ahora se usa MenuUtils para mostrar el menú

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
     * 
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
     * 
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

}
