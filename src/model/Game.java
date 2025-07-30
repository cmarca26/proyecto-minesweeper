package model;

// Clase que representa la logica principal del juego Buscaminas
public class Game {

    // Atributos principales del juego
    private Player player;
    private GameState state;
    private long startTime;
    private long endTime;

    // Constructor: inicializa el jugador y el estado
    public Game() {
        this.player = new Player();
        this.state = GameState.IN_PROGRESS;
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
}
