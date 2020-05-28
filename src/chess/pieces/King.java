package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K"; // Retorna a letra que corresponde a peça no tabuleiro
    }

    @Override
    public boolean[][] possibleMoves() {
        // Será implementado mais tarde
        return new boolean[0][];
    }
}
