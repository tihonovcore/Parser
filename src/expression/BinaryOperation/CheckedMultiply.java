package expression.BinaryOperation;

import expression.TripleExpression;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
       super(left, right);
    }

    @Override
    protected int result(int left, int right) throws OverflowException {
        check(left, right);
        return left * right;
    }

    private void check(int left, int right) throws OverflowException {
        if (left == 0) {
            return;
        }

        if (left > 0 && right > 0 && Integer.MAX_VALUE / left < right) {
            throw new OverflowException(""); //TODO add message
        } else if (left > 0 && right < 0 && Integer.MIN_VALUE / left > right) {
            throw new OverflowException(""); //TODO add message
        } else if (left != -1 && left < 0 && right > 0 && Integer.MIN_VALUE / left < right) {
            throw new OverflowException(""); //TODO add message
        } else if (left < 0 && right < 0 && Integer.MAX_VALUE / left > right) {
            throw new OverflowException(""); //TODO add message
        }
    }
}
