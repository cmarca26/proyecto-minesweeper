package persistence;

import model.Board;
import model.GameState;
import java.io.*;

/**
 * Clase encargada de la persistencia del juego - guardar y cargar partidas.
 */
public class FileManager {
    private static final String SAVE_DIRECTORY = "saves";
    private static final String FILE_EXTENSION = ".minesweeper";
    
    /**
     * Guarda el estado actual del juego en un archivo.
     * @param board El tablero a guardar
     * @param gameState El estado actual del juego
     * @param filename El nombre del archivo (sin extensión)
     * @throws IOException Si hay problemas al escribir el archivo
     */
    public static void saveGame(Board board, GameState gameState, String filename) throws IOException {
        // Crear directorio si no existe
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        
        String fullPath = SAVE_DIRECTORY + File.separator + filename + FILE_EXTENSION;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            // Crear objeto serializable con los datos del juego
            GameData gameData = new GameData(board, gameState);
            oos.writeObject(gameData);
        }
    }
    
    /**
     * Carga un juego guardado desde un archivo.
     * @param filename El nombre del archivo (sin extensión)
     * @return GameData con el tablero y estado cargados
     * @throws IOException Si hay problemas al leer el archivo
     * @throws ClassNotFoundException Si hay problemas al deserializar
     */
    public static GameData loadGame(String filename) throws IOException, ClassNotFoundException {
        String fullPath = SAVE_DIRECTORY + File.separator + filename + FILE_EXTENSION;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            return (GameData) ois.readObject();
        }
    }
    
    /**
     * Lista todos los archivos de guardado disponibles.
     * @return Array con los nombres de archivos (sin extensión)
     */
    public static String[] listSavedGames() {
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            return new String[0];
        }
        
        File[] files = saveDir.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null) {
            return new String[0];
        }
        
        String[] gameNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            // Remover la extensión
            gameNames[i] = name.substring(0, name.lastIndexOf('.'));
        }
        
        return gameNames;
    }
    
    /**
     * Elimina un archivo de guardado.
     * @param filename El nombre del archivo (sin extensión)
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean deleteGame(String filename) {
        String fullPath = SAVE_DIRECTORY + File.separator + filename + FILE_EXTENSION;
        File file = new File(fullPath);
        return file.exists() && file.delete();
    }
}
