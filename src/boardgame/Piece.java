package boardgame;

public abstract class Piece {
    // A classe foi criada como protected pois esta posição é uma posição simples de matriz e não uma
    // posição do jogo de Xadrez
    protected Position position; // Protected - acessível a mesma package
    private Board board;

    public Piece (Board board) {
        this.board = board;
        position = null; // posição de uma peça recém criada é NULL
    }
    // somente classes e subclasses dentro do mesmo pacote é que poderam acessar o tabuleiro de uma peça
    protected Board getBoard() {
        return board;
    }

    //Método abstrato. Como eu não sei quais são os movimentos possíveis (na classe Piece) eu
    // crio uma abstração do método e transformo a classe Piece em abstrata
    public abstract boolean[][] possibleMoves();

    // Método concreto, utilizando um método abstrato = Hook Methods
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves(); // Interessante uma MATRIZ do tipo boolean
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) { // Dica, estou usando o length aqui tmb, pq sei que a matriz do Xadrez é quadrada
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
