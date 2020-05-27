package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces; // Matriz de peças

    public Board(int rows, int columns) {
        // Programação defensiva. Validar tamanho do tabuleiro
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: There must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece (int row, int column) {
        // Se a posição não existir
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[row][column];
    }

    // Método em sobrecarga do superior, retornando posição
    public Piece piece (Position position) {
        // Se a posição não existir
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    // Dada uma determinada peça Piece, colocá-la nas coordenadas recuperadas pelos gets
    public void placePiece(Piece piece, Position position) {
        // Verificar se existe uma peça, antes de inserir
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position); // Recebe a peça que está no tabuleiro nesta posição
        aux.position = null; // Retiro a peça do tabuleiro
        pieces[position.getRow()][position.getColumn()] = null;
        return aux; // Contem a peça retirada
    }

    // Validar posições no tabuleiro
    private boolean positionExists(int row, int column) {
        // Validar pela dimensão do tabuleiro 8 x 8
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return piece(position) != null; // se dif de null, tem peça
    }
}
