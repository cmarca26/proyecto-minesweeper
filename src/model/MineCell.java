package model;

/**
 * Clase que representa una casilla con mina en el tablero de Buscaminas.
 * Extiende de Cell y implementa el comportamiento específico para minas.
 */
public class MineCell extends Cell {
    
    /**
     * Constructor de la casilla con mina
     * @param row Fila de la casilla
     * @param col Columna de la casilla
     */
    public MineCell(int row, int col) {
        super(row, col);
    }
    
    /**
     * Verifica si la casilla contiene una mina
     * @return true siempre, ya que es una casilla con mina
     */
    @Override
    public boolean isMine() {
        return true;
    }
    
    /**
     * Obtiene el valor a mostrar de la casilla
     * @return 'X' si está revelada y contiene mina, 'F' si tiene bandera, '?' si no está revelada
     */
    @Override
    public char getDisplayValue() {
        if (isFlagged) {
            return 'F';
        }
        if (isRevealed) {
            return 'X';
        }
        return '?';
    }
}
