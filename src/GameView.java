package view;

import controller.GameController;
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

        // Pide el número de minas al usuario
        System.out.println("10 minas por default(Si el numero no es valido)");
        System.out.print("Ingrese número de minas: ");
        String minesInput = scanner.nextLine();
        if (!minesInput.isEmpty()) {
            try {
                int mines = Integer.parseInt(minesInput);
                controller.setNumberOfMines(mines); // Agrega este método al controlador si quieres hacerlo dinámico
            } catch (NumberFormatException e) {
                System.out.println("Número no válido. Se usará valor por defecto.");
            }
        }

        //Inicia al juego, es decir indica al controlador que prepare el tablero, estado del juego ,minas, etc
        controller.startGame();

        //Se repite mientras el juego no termine
        while (!controller.isGameOver()) {
            controller.displayBoard(false);
            System.out.println("Ingrese la acción a ejecutar (d) Descubrir o (m) Marcar:");
            String action = scanner.nextLine().toLowerCase();

            System.out.println("Ingrese coordenadas (Ejm: A5):");
            String coord = scanner.nextLine().toUpperCase();

            if (coord.length() < 2) {
                System.out.println("Las coordenadas ingresadas son inválidas. Intente de nuevo.");
                continue;
            }

            //La primera letra la convierte en un indice numerico
            char rowChar = coord.charAt(0);
            int row = rowChar - 'A'; // convierte 'A' a 0, 'B' a 1, etc.

            //Convierte los caractres restantes a numero para la columna
            //si no es valido, vuelve al inicio del bucle
            int col;
            try {
                col = Integer.parseInt(coord.substring(1)) - 1; // convierte '5' a 4
            } catch (NumberFormatException e) {
                System.out.println("Las coordenadas ingresadas de la columna es inválida.");
                continue;
            }

            //Comprueba si la accion es valida
            boolean validMove = false;

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
}

