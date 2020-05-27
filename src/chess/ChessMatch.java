package chess;

import boardgame.Board;

// Esta classe tem as regras do jogo do Xadrez
public class ChessMatch {

    private Board board;

    // Definir a dimensão do tabuleiro do Xadrez
    public ChessMatch() {
        board = new Board(8,8);
    }

    //Retorna a matriz de peças de Xadrez correspondentes a esta partida
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        // Percorrer a matriz
        for (int i = 0; i < board.getRows() ; i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                // Fazer o downcasting para ChessPiece para interpretar como uma peça de Xadrez
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
}
