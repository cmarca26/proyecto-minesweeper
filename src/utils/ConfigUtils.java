package utils;

/**
 * Utilidad para obtener y validar configuraciones del juego desde consola.
 */
import java.util.Scanner;
import model.Board;

public class ConfigUtils {
    /**
     * Solicita al usuario el número de minas con validación
     * @param scanner Scanner para leer entrada
     * @return número de minas válido
     */
    public static int getMineCountFromUser(Scanner scanner) {
        while (true) {
            System.out.println("\n=== CONFIGURACION DEL JUEGO ===");
            System.out.println("Ingrese el numero de minas (" + Board.getValidMineRange() + "):");
            System.out.print("Numero de minas: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: Debe ingresar un numero.");
                continue;
            }
            try {
                int numMines = Integer.parseInt(input);
                if (Board.isValidMineCount(numMines)) {
                    return numMines;
                } else {
                    System.out.println("Error: El numero de minas debe estar entre " + Board.getValidMineRange() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + input + "' no es un numero valido. Por favor ingrese solo numeros.");
            }
        }
    }
}
