package expression.binaryOperation;

import expression.TripleExpression;
import expression.exceptions.EvaluatingException;

public abstract class AbstractBinaryOperation implements TripleExpression {
    private TripleExpression left;
    private TripleExpression right;

    AbstractBinaryOperation(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int result(int left, int right) throws EvaluatingException;

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return result(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
