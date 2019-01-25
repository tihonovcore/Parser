package expression.BinaryOperation;

import expression.TripleExpression;

public class Divide extends AbstractBinaryOperation {
    public Divide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left / right;
    }
}
