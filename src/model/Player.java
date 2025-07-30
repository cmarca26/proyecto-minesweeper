package model;

// Clase que representa un jugador en el juego.
public class Player {

    // Atributos del jugador
    private String name;
    private int score;

    // Constructores
    public Player() {
        this.name = "Player";
        this.score = 0;
    }

    // Constructor con nombre
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
