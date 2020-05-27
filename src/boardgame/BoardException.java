package boardgame;

// RuntimeException é uma exceção opcional de ser tratada
public class BoardException extends RuntimeException{
    private static final long serialVersionUID =  1L;

    // Repassar a mensagem para a super classe RuntimeException
    public BoardException(String msg) {
        super(msg);
    }

}
