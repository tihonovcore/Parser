package expression.BinaryOperation;

import expression.TripleExpression;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(TripleExpression left, TripleExpression right) {
       super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left * right;
    }
}
