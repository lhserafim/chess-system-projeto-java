package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Faz a impressão do Xadrez na tela
public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // Limpar tela
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        /*
        Posições no Xadrez são a1, b2, c1, etc
        Leio a posição informada, recorto em 2 - coluna e linha
         */
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (InputMismatchException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        // Imprimir a partida é imprimir o tabuleiro o turno
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
        if (chessMatch.getCheck()) {
            System.out.println("CHECK!");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " "); // Imprime a numeração lateral
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j],false); // Imprimir as peças SEM os movimentos possíveis
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h"); // imprime o alfabeto no rodapé
    }

    // trabalhando com sobrecarga
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " "); // Imprime a numeração lateral
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j],possibleMoves[i][j]); // Imprimir as peças COM os movimentos possíveis
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h"); // imprime o alfabeto no rodapé
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background == true) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" "); // Para não ficarem grudados
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        // Usando expressões Lambda
        // Transformo minha lista em stream e coloco um predicado (x -> alguma coisa).
        // Filtro tod o mundo de cor branca
        // transformo em lista collect(Collectors.toList()
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Captured pieces: ");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        // Macete p/ imprimir array no java
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        // Macete p/ imprimir array no java
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
}
