package expression.binaryOperation;

import expression.TripleExpression;
import expression.exceptions.OverflowException;

public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) throws OverflowException {
        check(left, right);
        return left - right;
    }

    private void check(int left, int right) throws OverflowException {
        if (left >= 0 && right < 0 && Integer.MAX_VALUE + right < left) {
            throw new OverflowException(left +  " - " + right + " isn't int-value");
        } else if (left <= 0 && right > 0 &&  Integer.MIN_VALUE + right > left) {
            throw new OverflowException(left +  " - " + right + " isn't int-value");
        }
    }
}
