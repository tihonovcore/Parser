package expression.binaryOperation;

import expression.TripleExpression;

public class And extends AbstractBinaryOperation {
    public And(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left & right;
    }
}
