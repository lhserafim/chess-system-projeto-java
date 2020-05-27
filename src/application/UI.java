package application;

import chess.ChessPiece;

// Faz a impressão do Xadrez na tela
public class UI {
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " "); // Imprime a numeração lateral
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]); // Imprimir as peças
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h"); // imprime o alfabeto no rodapé
    }

    private static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        }
        else {
            System.out.print(piece);
        }
        System.out.print(" "); // Para não ficarem grudados
    }
}
