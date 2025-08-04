package utils;

/**
 * Utilidad para validar y convertir coordenadas de entrada del usuario.
 */
public class CoordinateUtils {
    /**
     * Valida y convierte coordenadas tipo "A5" a [fila, columna].
     * @param coord coordenada como string
     * @return array [fila, columna] o null si no es valido
     */
    public static int[] parseCoordinates(String coord) {
        if (coord == null || coord.length() < 2) return null;
        char rowChar = coord.charAt(0);
        if (!Character.isLetter(rowChar)) return null;
        int row = Character.toUpperCase(rowChar) - 'A';
        if (row < 0 || row >= 10) return null;
        try {
            int col = Integer.parseInt(coord.substring(1)) - 1;
            if (col < 0 || col >= 10) return null;
            return new int[]{row, col};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
