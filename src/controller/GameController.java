package controller;

import model.*;

// Clase que representa la logica principal del juego Buscaminas
public class GameController {
    
    // Atributos principales del juego
    private Game game;
    private Board board;

    public GameController() {
        board = new Board(); 
        game = new Game();
    }
    
    // pedir nombre del jugador
    public void setPlayerName(String name) {
        game.getPlayer().setName(name);
    }
    
    // pedir numero de minas
    public void setNumberOfMines(int mines) {
        // Aquí tendrías que permitir al tablero aceptar minas variables
        // Si no está implementado, omite esta función o cúrratela.
    }
    
    //Este metodo es el que inicia el juego
    //pone el estado del juego "jugando", inicializa el tiempo, etc
    public void startGame() {
        game.start();
    }
    
    /*Comprueba si el juego ha terminado
      Si el juego termina retorna true 
      Si el juego termina retorna false
    */
    public boolean isGameOver() {
        if (board.isGameWon()) {
            game.finish();  // o game.setState(GameState.WON);
            return true;
        }
        return game.isGameOver();
    }
    
    /*Muestra por la consola el tablero
      @param showAll indica si mostrar todas las minas al final del juego
    */
    public void displayBoard(boolean showAll) {
        board.displayBoard(showAll);
    }
    
    /*Si descubre una mina termina el juego y muestra los resultados
      @param row es la fila
      @param col es la columna 
    */
    public boolean revealCell(int row, int col) {
        boolean safe = board.revealCell(row, col);
        if (!safe) {
            game.finish(); // Juego perdido
            board.revealAllMines();
        } else if (board.isGameWon()) {
            game.reset(); // O setear estado a WON
            game.showResults();
        }
        return safe;
    }
    
    /*Marca o desmarca una celda con una bandera
      @param row es la fila
      @param col es la columna 
    */
    public void toggleFlag(int row, int col) {
        board.toggleFlag(row, col);
    }
    
    //Muestra el resultado del juego
    public void showResults() {
        game.showResults();
    }
}
