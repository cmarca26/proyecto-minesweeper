package controller;

import model.*;
import exceptions.*;
import persistence.*;
import java.io.IOException;

// Clase que representa la logica principal del juego Buscaminas
public class GameController {
    
    // Atributos principales del juego
    private Game game;
    private Board board;

    public GameController() {
        board = new Board(); 
        game = new Game();
    }
    
    /**
     * Constructor con número específico de minas
     * @param numMines número de minas para el tablero
     */
    public GameController(int numMines) {
        board = new Board(numMines); 
        game = new Game();
    }
    
    // pedir nombre del jugador
    public void setPlayerName(String name) {
        game.getPlayer().setName(name);
    }
    
    // obtener nombre del jugador
    public String getPlayerName() {
        return game.getPlayer().getName();
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
      @throws InvalidMoveException si la casilla ya está revelada o es inválida
      @throws GameAlreadyOverException si el juego ya ha terminado
    */
    public boolean revealCell(int row, int col) throws InvalidMoveException, GameAlreadyOverException {
        // Verificar si el juego ya ha terminado
        if (game.getGameState() != GameState.IN_PROGRESS) {
            throw new GameAlreadyOverException("No se puede revelar casillas - el juego ha terminado");
        }
        
        // Verificar coordenadas válidas
        if (row < 0 || row >= Board.BOARD_SIZE || col < 0 || col >= Board.BOARD_SIZE) {
            throw new InvalidMoveException("Coordenadas inválidas: (" + row + ", " + col + ")");
        }
        
        // Verificar si la casilla ya está revelada
        if (board.getCell(row, col).isRevealed()) {
            throw new InvalidMoveException("La casilla (" + row + ", " + col + ") ya está revelada");
        }
        
        // Verificar si la casilla está marcada con bandera
        if (board.getCell(row, col).isFlagged()) {
            throw new InvalidMoveException("No se puede revelar una casilla marcada con bandera");
        }
        
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
    public void toggleFlag(int row, int col) throws GameAlreadyOverException {
        if (game.getGameState() != GameState.IN_PROGRESS) {
            throw new GameAlreadyOverException("No se puede marcar casillas - el juego ha terminado");
        }
        board.toggleFlag(row, col);
    }
    
    /**
     * Guarda el juego actual en un archivo.
     * @param filename Nombre del archivo donde guardar
     * @throws IOException Si hay problemas al guardar
     */
    public void saveGame(String filename) throws IOException {
        FileManager.saveGame(board, game.getGameState(), filename);
    }
    
    /**
     * Carga un juego desde un archivo.
     * @param filename Nombre del archivo a cargar
     * @throws IOException Si hay problemas al cargar
     * @throws ClassNotFoundException Si hay problemas de deserialización
     */
    public void loadGame(String filename) throws IOException, ClassNotFoundException {
        GameData gameData = FileManager.loadGame(filename);
        this.board = gameData.getBoard();
        this.game.setGameState(gameData.getGameState());
    }
    
    /**
     * Lista todos los juegos guardados.
     * @return Array con nombres de archivos guardados
     */
    public String[] listSavedGames() {
        return FileManager.listSavedGames();
    }
    
    /**
     * Elimina un juego guardado.
     * @param filename Nombre del archivo a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean deleteGame(String filename) {
        return FileManager.deleteGame(filename);
    }
    
    //Muestra el resultado del juego
    public void showResults() {
        game.showResults();
    }
}
