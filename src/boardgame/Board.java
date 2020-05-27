package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces; // Matriz de peças

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Piece piece (int row, int column) {
        return pieces[row][column];
    }

    // Método em sobrecarga do superior, retornando posição
    public Piece piece (Position position) {
        return pieces[position.getRow()][position.getColumn()];
    }

    // Dada uma determinada peça Piece, colocá-la nas coordenadas recuperadas pelos gets
    public void placePiece(Piece piece, Position position) {
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
}
