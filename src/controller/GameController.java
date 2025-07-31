package controller;

import model.*;

// Clase que representa la logica principal del juego Buscaminas
public class GameController {

    // Atributos principales del juego
    private Player player;
    private GameState state;
    private Board board;
    private long startTime;
    private long endTime;

    // Constructor: inicializa el jugador y el estado
    public GameController() {
        this.player = new Player();
        this.state = GameState.IN_PROGRESS;
        this.board = new Board();
    }

    // Inicia el juego y registra el tiempo de inicio
    public void start() {
        startTime = System.currentTimeMillis();
        state = GameState.IN_PROGRESS;
    }

    // Finaliza el juego
    public void finish() {
        this.state = GameState.LOST;
        this.endTime = System.currentTimeMillis();
    }

    // Reinicia el juego con nuevos parametros
    public void reset() {
        this.player = new Player();
        this.state = GameState.IN_PROGRESS;
        this.board = new Board();
        this.startTime = System.currentTimeMillis();
        this.endTime = 0;
    }

    // Devuelve el estado actual del juego
    public GameState getState() {
        return state;
    }

    // Devuelve el jugador
    public Player getPlayer() {
        return player;
    }

    // Devuelve el puntaje actual del jugador
    public int getScore() {
        return player.getScore();
    }

    // Devuelve el tiempo jugado en segundos
    public long getElapsedTime() {
        if (state == GameState.IN_PROGRESS) {
            return (System.currentTimeMillis() - startTime) / 1000;
        } else {
            return (endTime - startTime) / 1000;
        }
    }
    
    // Devuelve el tablero para que ConsoleView pueda acceder
    public Board getBoard() {
        return board;
    }
    
    // Realiza un movimiento del jugador (revelar casilla)
    public boolean makeMove(int row, int col) {
        if (state != GameState.IN_PROGRESS) {
            return false; // No se puede jugar si ya terminó
        }
        
        boolean safe = board.revealCell(row, col);
        
        if (!safe) {
            // Se reveló una mina - DERROTA
            this.state = GameState.LOST;
            this.endTime = System.currentTimeMillis();
            board.revealAllMines();
            return false;
        } else if (board.isGameWon()) {
            // Se revelaron todas las casillas seguras - VICTORIA
            this.state = GameState.WON;
            this.endTime = System.currentTimeMillis();
            return true;
        }
        
        return true; // Movimiento exitoso, juego continúa
    }
    
    // Marca o desmarca una bandera
    public void toggleFlag(int row, int col) {
        if (state == GameState.IN_PROGRESS) {
            board.toggleFlag(row, col);
        }
    }
}
