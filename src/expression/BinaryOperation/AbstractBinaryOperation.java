package expression.BinaryOperation;

import expression.TripleExpression;

public abstract class AbstractBinaryOperation implements TripleExpression {
    TripleExpression left;
    TripleExpression right;

    AbstractBinaryOperation(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int result(int left, int right);

    @Override
    public int evaluate(int x, int y, int z) {
        return result(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
