package expression.BinaryOperation;

import expression.TripleExpression;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) throws EvaluatingException {
        check(left, right);
        return left / right;
    }

    private void check(int left, int right) throws EvaluatingException {
        if (right == 0) {
            throw new DivisionByZeroException(""); //TODO add message
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException("");
        }
    }
}
