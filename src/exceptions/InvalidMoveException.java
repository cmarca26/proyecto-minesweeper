package exceptions;

/**
 * Excepción lanzada cuando se intenta hacer un movimiento inválido en el juego.
 */
public class InvalidMoveException extends Exception {
    
    public InvalidMoveException(String message) {
        super(message);
    }
    
    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
