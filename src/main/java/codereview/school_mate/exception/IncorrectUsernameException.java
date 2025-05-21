package codereview.school_mate.exception;

public class IncorrectUsernameException extends RuntimeException{
    public IncorrectUsernameException(String message) {
        super(message);
    }
}
