package exceptions;

/**
 * Excepción lanzada cuando se intenta realizar una acción en un juego que ya ha terminado.
 */
public class GameAlreadyOverException extends Exception {
    
    public GameAlreadyOverException(String message) {
        super(message);
    }
    
    public GameAlreadyOverException(String message, Throwable cause) {
        super(message, cause);
    }
}
