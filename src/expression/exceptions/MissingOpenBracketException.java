package expression.exceptions;

public class MissingOpenBracketException extends ParsingException {
    public MissingOpenBracketException(String message) {
        super(message);
    }
}
