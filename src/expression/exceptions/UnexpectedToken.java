package expression.exceptions;

public class UnexpectedToken extends ParsingException {
    public UnexpectedToken(String message) {
        super(message);
    }
}
