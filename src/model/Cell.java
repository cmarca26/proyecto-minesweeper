package model;

/**
 * Clase abstracta que representa una casilla del tablero de Buscaminas.
 * Utiliza polimorfismo para manejar diferentes tipos de casillas.
 */
public abstract class Cell {
    
    protected boolean isRevealed;
    protected boolean isFlagged;
    protected int row;
    protected int col;
    
    /**
     * Constructor de la casilla
     * @param row Fila de la casilla
     * @param col Columna de la casilla
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isRevealed = false;
        this.isFlagged = false;
    }
    
    /**
     * Revela la casilla
     */
    public void reveal() {
        if (!isFlagged) {
            this.isRevealed = true;
        }
    }
    
    /**
     * Marca o desmarca una bandera en la casilla
     */
    public void toggleFlag() {
        if (!isRevealed) {
            this.isFlagged = !this.isFlagged;
        }
    }
    
    /**
     * Verifica si la casilla está revelada
     * @return true si está revelada, false en caso contrario
     */
    public boolean isRevealed() {
        return isRevealed;
    }
    
    /**
     * Verifica si la casilla tiene bandera
     * @return true si tiene bandera, false en caso contrario
     */
    public boolean isFlagged() {
        return isFlagged;
    }
    
    /**
     * Obtiene la fila de la casilla
     * @return número de fila
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Obtiene la columna de la casilla
     * @return número de columna
     */
    public int getCol() {
        return col;
    }
    
    /**
     * Método abstracto para verificar si la casilla contiene una mina
     * @return true si contiene mina, false en caso contrario
     */
    public abstract boolean isMine();
    
    /**
     * Método abstracto para obtener el valor a mostrar de la casilla
     * @return carácter que representa la casilla
     */
    public abstract char getDisplayValue();
}
