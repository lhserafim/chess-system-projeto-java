package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {
    private Color color;

    // Como a classe Piece tem um construtor com argumentos, necessário criá-lo aqui pq extendi Piece
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
