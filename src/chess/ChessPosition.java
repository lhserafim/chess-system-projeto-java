package chess;

import boardgame.Position;

// A posição do Xadrez é dada em letra e número
public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) { // posso comparar a  < que algo
            throw new ChessException("Error. Valid values are from a1 to 8h.");
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    // # = protected
    protected Position toPosition() {
        // Lógica p/ localizar as posições do Xadrez a1, a2, etc em valores de matriz
        // As letras tem valor: a = 0; b = 1; c = 2... Portanto a - a = 0; b - a = 1; c - a = 2...
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        // Necessário fazer o cast de char
        return new ChessPosition((char)('a' + position.getColumn()),8 - position.getRow());
    }

    @Override
    public String toString() {
        // O string vazio é um macete para concatenar automático. Se tirar o compilador não vai aceitar
        // assim forçamos o compilador a entender que é uma concatenação de strings
        return "" + column + row; // vai imprimir a1 ; b2 , etc
    }
}
