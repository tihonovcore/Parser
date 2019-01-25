package expression.BinaryOperation;

import expression.TripleExpression;

public class Subtract extends AbstractBinaryOperation {
    public Subtract(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left - right;
    }
}
