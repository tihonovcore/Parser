package expression.UnaryOperation;

import expression.TripleExpression;
import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(TripleExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int result = expression.evaluate(x, y, z);
        if (result == Integer.MIN_VALUE) {
            throw new OverflowException(""); //TODO add message
        }
        return -result;
    }
}
