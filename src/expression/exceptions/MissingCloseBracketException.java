package expression.exceptions;

public class MissingCloseBracketException extends ParsingException {
    public MissingCloseBracketException(String message) {
        super(message);
    }
}
