package persistence;

import model.Board;
import model.GameState;
import java.io.Serializable;

/**
 * Clase para encapsular los datos del juego que se serializan.
 */
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Board board;
    private final GameState gameState;
    
    public GameData(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public GameState getGameState() {
        return gameState;
    }
}
