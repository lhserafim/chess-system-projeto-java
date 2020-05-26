package boardgame;

public class Piece {
    // A classe foi criada como protected pois esta posição é uma posição simples de matriz e não uma
    // posição do jogo de Xadrez
    protected Position position;
    private Board board;

    public Piece (Board board) {
        this.board = board;
        position = null; // posição de uma peça recém criada é NULL
    }
    // somente classes e subclasses dentro do mesmo pacote é que poderam acessar o tabuleiro de uma peça
    protected Board getBoard() {
        return board;
    }
}
