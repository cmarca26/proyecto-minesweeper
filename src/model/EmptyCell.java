package model;

/**
 * Clase que representa una casilla vacía en el tablero de Buscaminas.
 * Extiende de Cell y implementa el comportamiento específico para casillas sin minas.
 */
public class EmptyCell extends Cell {
    
    private int adjacentMines;
    
    /**
     * Constructor de la casilla vacía
     * @param row Fila de la casilla
     * @param col Columna de la casilla
     */
    public EmptyCell(int row, int col) {
        super(row, col);
        this.adjacentMines = 0;
    }
    
    /**
     * Verifica si la casilla contiene una mina
     * @return false siempre, ya que es una casilla vacía
     */
    @Override
    public boolean isMine() {
        return false;
    }
    
    /**
     * Establece el número de minas adyacentes
     * @param count número de minas adyacentes
     */
    public void setAdjacentMines(int count) {
        this.adjacentMines = count;
    }
    
    /**
     * Obtiene el número de minas adyacentes
     * @return número de minas adyacentes
     */
    public int getAdjacentMines() {
        return adjacentMines;
    }
    
    /**
     * Obtiene el valor a mostrar de la casilla
     * @return 'F' si tiene bandera, número de minas adyacentes si está revelada,
     *         'V' si está revelada y no tiene minas adyacentes, '?' si no está revelada
     */
    @Override
    public char getDisplayValue() {
        if (isFlagged) {
            return 'F';
        }
        if (isRevealed) {
            if (adjacentMines == 0) {
                return 'V';  // Espacio vacío según las especificaciones
            } else {
                return (char) ('0' + adjacentMines);  // Convierte número a carácter
            }
        }
        return '?';
    }
}
