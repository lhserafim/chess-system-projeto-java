package chess;

import boardgame.Board;
import boardgame.Piece;
import chess.pieces.King;
import chess.pieces.Rook;

// Esta classe tem as regras do jogo do Xadrez
public class ChessMatch {

    private Board board;

    // Definir a dimensão do tabuleiro do Xadrez
    public ChessMatch() {
        board = new Board(8,8);
        initialSetup();
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

    // Colocando as peças no tabuleiro usando a posição do Xadrez (a1, b2, etc)
    private void placeNewPiece(char column, int row, Piece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // Converten p/ posição de matriz usando toPosition()
    }

    // Fazer o instanciamento do tabuleiro de Xadrez, colocar as peças para começar o jogo
    private void initialSetup() {
        // Instanciar as peças via Matriz
        //board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));

        // Instanciar as peças usando coordenadas do Xadres
        placeNewPiece('b',6,new Rook(board, Color.WHITE));
        placeNewPiece('e',8,new King(board, Color.BLACK));
        placeNewPiece('e',1,new King(board, Color.WHITE));
    }
}