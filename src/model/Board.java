package model;

import java.util.Random;

/**
 * Clase que representa el tablero del juego Buscaminas.
 * Maneja la matriz de celdas, colocación de minas y lógica del juego.
 */
public class Board {
    
    public static final int BOARD_SIZE = 10;
    public static final int TOTAL_MINES = 10;
    
    private Cell[][] cells;
    private int revealedCells;
    private Random random;
    
    /**
     * Constructor del tablero
     */
    public Board() {
        this.cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        this.revealedCells = 0;
        this.random = new Random();
        initializeBoard();
        placeMines();
        calculateAdjacentMines();
    }
    
    /**
     * Inicializa el tablero con casillas vacías
     */
    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cells[row][col] = new EmptyCell(row, col);
            }
        }
    }
    
    /**
     * Coloca minas aleatoriamente en el tablero
     */
    private void placeMines() {
        int minesPlaced = 0;
        
        while (minesPlaced < TOTAL_MINES) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            
            // Verifica que no haya ya una mina en esa posición
            if (!cells[row][col].isMine()) {
                cells[row][col] = new MineCell(row, col);
                minesPlaced++;
            }
        }
    }
    
    /**
     * Calcula el número de minas adyacentes para cada casilla vacía
     */
    private void calculateAdjacentMines() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (!cells[row][col].isMine()) {
                    int count = countAdjacentMines(row, col);
                    ((EmptyCell) cells[row][col]).setAdjacentMines(count);
                }
            }
        }
    }
    
    /**
     * Cuenta las minas adyacentes a una posición específica
     * @param row fila de la casilla
     * @param col columna de la casilla
     * @return número de minas adyacentes
     */
    private int countAdjacentMines(int row, int col) {
        int count = 0;
        
        // Verifica las 8 casillas adyacentes (incluyendo diagonales)
        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue; // No cuenta la casilla actual
                }
                
                int newRow = row + deltaRow;
                int newCol = col + deltaCol;
                
                // Valida bordes del tablero
                if (isValidPosition(newRow, newCol) && cells[newRow][newCol].isMine()) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Valida si una posición está dentro de los límites del tablero
     * @param row fila a validar
     * @param col columna a validar
     * @return true si la posición es válida, false en caso contrario
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
    
    /**
     * Revela una casilla del tablero
     * @param row fila de la casilla
     * @param col columna de la casilla
     * @return true si se reveló exitosamente, false si era una mina
     */
    public boolean revealCell(int row, int col) {
        if (!isValidPosition(row, col) || cells[row][col].isRevealed() || cells[row][col].isFlagged()) {
            return true; // No hace nada si es inválida, ya revelada o tiene bandera
        }
        
        Cell cell = cells[row][col];
        cell.reveal();
        
        if (cell.isMine()) {
            return false; // El juego termina, se reveló una mina
        }
        
        revealedCells++;
        
        // Si es una casilla vacía sin minas adyacentes, revela recursivamente
        if (!cell.isMine() && ((EmptyCell) cell).getAdjacentMines() == 0) {
            revealAdjacentCells(row, col);
        }
        
        return true;
    }
    
    /**
     * Revela recursivamente las casillas adyacentes vacías
     * @param row fila de la casilla central
     * @param col columna de la casilla central
     */
    private void revealAdjacentCells(int row, int col) {
        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }
                
                int newRow = row + deltaRow;
                int newCol = col + deltaCol;
                
                if (isValidPosition(newRow, newCol)) {
                    Cell adjacentCell = cells[newRow][newCol];
                    
                    if (!adjacentCell.isRevealed() && !adjacentCell.isFlagged() && !adjacentCell.isMine()) {
                        adjacentCell.reveal();
                        revealedCells++;
                        
                        // Continúa la recursión si la casilla adyacente también está vacía
                        if (((EmptyCell) adjacentCell).getAdjacentMines() == 0) {
                            revealAdjacentCells(newRow, newCol);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Marca o desmarca una bandera en una casilla
     * @param row fila de la casilla
     * @param col columna de la casilla
     */
    public void toggleFlag(int row, int col) {
        if (isValidPosition(row, col)) {
            cells[row][col].toggleFlag();
        }
    }
    
    /**
     * Verifica si el jugador ha ganado el juego
     * @return true si todas las casillas sin minas están reveladas
     */
    public boolean isGameWon() {
        int totalSafeCells = (BOARD_SIZE * BOARD_SIZE) - TOTAL_MINES;
        return revealedCells == totalSafeCells;
    }
    
    /**
     * Obtiene una casilla específica del tablero
     * @param row fila de la casilla
     * @param col columna de la casilla
     * @return la casilla en la posición especificada, null si es inválida
     */
    public Cell getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return cells[row][col];
        }
        return null;
    }
    
    /**
     * Obtiene el tamaño del tablero
     * @return tamaño del tablero
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }
    
    /**
     * Obtiene el número total de minas
     * @return número total de minas
     */
    public int getTotalMines() {
        return TOTAL_MINES;
    }
    
    /**
     * Obtiene el número de casillas reveladas
     * @return número de casillas reveladas
     */
    public int getRevealedCells() {
        return revealedCells;
    }
    
    /**
     * Revela todas las minas (para mostrar al final del juego)
     */
    public void revealAllMines() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (cells[row][col].isMine()) {
                    cells[row][col].reveal();
                }
            }
        }
    }
    
    /**
     * Método para mostrar el tablero en consola (útil para debug)
     *
     * @param showAll si es true, muestra todas las casillas incluyendo minas
     */
    public void displayBoard(boolean showAll) {
        System.out.println("\n    1   2   3   4   5   6   7   8   9  10");
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(letters[row] + "  ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                Cell cell = cells[row][col];
                String displayValue = "?";

                if (showAll) {
                    if (cell.isMine()) {
                        displayValue = "X";
                    } else if (cell instanceof EmptyCell) {
                        int adjacent = ((EmptyCell) cell).getAdjacentMines();
                        displayValue = (adjacent == 0) ? "V" : Integer.toString(adjacent);
                    }
                    System.out.print(String.format("[%1s] ", displayValue));
                } else {
                    if (cell.isRevealed() || cell.isFlagged()) {
                        displayValue = cell.getDisplayValue() + ""; // convierte char a String
                        System.out.print(String.format("[%1s] ", displayValue));
                    } else {
                        System.out.print("[?] ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
