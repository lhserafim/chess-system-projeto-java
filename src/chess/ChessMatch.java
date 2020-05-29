package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Esta classe tem as regras do jogo do Xadrez
public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    // Definir a dimensão do tabuleiro do Xadrez
    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() { return check; }

    public boolean getCheckMate() { return checkMate;}

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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        // Converter posições do Xadrez para Matriz
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        // Verificar se o jogador se colocou em cheque
        if (testCheck(currentPlayer)) {
            undoMove(source,target,capturedPiece); // Desfazer o movimento
            throw new ChessException("You can't put yourself in check");
        }

        // Expressão condicional ternária
        // Testar se o oponente ficou em cheque
        check = (testCheck(opponent(currentPlayer))) ? true : false;

        // Testar se o movimento que eu fiz, colocou o oponente em checkmate
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else{
            nextTurn();
        }
        return (ChessPiece)capturedPiece; // Fazer downcasting porque a peça é do tipo Piece
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        // Validar se uma peça foi capturada para remover da lista
        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    // Desfazer a jogada
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);

        // Retornar peça para o tabuleiro, caso tenha sido retirada no movimento
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on the source position");
        }
        // Precisei fazer o downcasting para chamar .getColor()
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        // Acessa o tabuleiro na posição de origem e valida se tem movimentos possíveis
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn() {
        turn ++; // incrementar o turno
        // Expreção ternária: Como se fosse um DECODE ou IF
        // Se o jogador atual for branco agora ele vai ser BLACK
        // DECODE(currentPlayer,Color.WHITE,Color.BLACK, Color.WHITE)
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        // Escrevendo como se fosse um DECODE
        // DECODE(color, Color.WHITE, Color.BLACK, Color.WHITE)
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        // Crio uma lista do tipo Piece.
        // Pego as peças do tabuleiro com o piecesOnTheBoard e filtro usando a expressão lambda
        // "tal que" x -> x.getColor() == color (que veio do argumento)
        // mas para funcionar, faço o casting para ChessPiece pois é esta classe que tem a cor!!
        // depois converto para lista novamente .collect(Collectors.toList()
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) { // se esta peça for uma instancia de King
                return (ChessPiece)p; // fazer downcasting p/ ChessPiece
            }
        }
        // Estou usando uma exceção do JAVA pois é um tipo de erro não previsto, seria um crash no programa. Portanto
        // o uso de exceçao do JAVA
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        // Pego a posição do meu rei no formato de Matriz
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves(); // matriz de movimentos possíveis da peça p
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        // Se não estiver em check, não pode estar em checkMate
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            // pegar os movimentos possíveis desta peça
            boolean[][] mat = p.possibleMoves();
            // Percorrer a matriz
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    // Verificar se cada linha da matriz é um movimento possível
                    if (mat[i][j]) {
                        // Ver se o movimento possível tira do check
                        // para pegar a posição desta peça, fazer um downcast para ChessPiece (pq a position é protected)
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i,j);
                        // Fazer o movimento para testar se sai do check
                        Piece capturedPiece = makeMove(source, target);
                        // Verificar se ainda está em check
                        boolean testCheck = testCheck(color);
                        // Desfazer o movimento, pois era apenas um teste
                        undoMove(source, target, capturedPiece);
                        // Se não estava em check retorna false
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // Colocando as peças no tabuleiro usando a posição do Xadrez (a1, b2, etc)
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // Converten p/ posição de matriz usando toPosition()
        // Adiciona a lista de peças no tabuleiro
        piecesOnTheBoard.add(piece);
    }

    // Fazer o instanciamento do tabuleiro de Xadrez, colocar as peças para começar o jogo
    private void initialSetup() {
        // Instanciar as peças via Matriz
        //board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));

        // Instanciar as peças usando coordenadas do Xadres
        /*
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
         */

        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
    }
}
