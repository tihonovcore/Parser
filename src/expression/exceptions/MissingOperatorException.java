package expression.exceptions;

public class MissingOperatorException extends ParsingException {
    public MissingOperatorException(String message) {
        super(message);
    }
}
