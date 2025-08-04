package utils;

/**
 * Utilidad para mostrar el menú de juego.
 */
public class MenuUtils {
    public static void showGameMenu() {
        System.out.println("\n=== OPCIONES DE JUEGO ===");
        System.out.println("(d) Descubrir casilla");
        System.out.println("(m) Marcar/Desmarcar bandera");
        System.out.println("(g) Guardar juego");
        System.out.println("(c) Cargar juego");
        System.out.println("(l) Listar juegos guardados");
        System.out.println("(s) Guardar y salir");
        System.out.print("Elija una opcion: ");
    }
}
