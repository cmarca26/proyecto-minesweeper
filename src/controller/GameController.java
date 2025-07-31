package controller;

import model.*;

// Clase que representa la logica principal del juego Buscaminas
public class GameController {

    // Atributos principales del juego
    private Game game;
    private Board board;

    public void startGame() {

        // pedir nombre del jugador

        // pedir numero de minas

        game = new Game();
        game.start();

        while (!game.isGameOver()) {

            // mostrar el tablero
            board.displayBoard(false);

            // pedir accion al jugador (descubrir o marcar celda)
            // pedir coordenadas de la celda

            // convertir coordenadas a fila y columna

            // segun
            // si es descubrir celda
            // si es marcar celda
        }

        // mostrar el tablero
        board.displayBoard(false);
        game.showResults();
    }
}
