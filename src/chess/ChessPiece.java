package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

// Como transformei a Piece em uma classe abstrata, tenho 2 alternativas p/ esta classe:
// - Implementar o método da classe abstrata (neste caso não é possível, pois esta classe não conhece os movimentos)
// - Transformar a classe em abstrata (neste caso é a solução)
public abstract class ChessPiece extends Piece {
    private Color color;

    // Como a classe Piece tem um construtor com argumentos, necessário criá-lo aqui pq extendi Piece
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position); // recebe a peça que está na posição // fazer downcast
        return p != null && p.getColor() != color;
    }
}
