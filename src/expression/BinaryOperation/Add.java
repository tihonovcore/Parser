package expression.BinaryOperation;

import expression.TripleExpression;

public class Add extends AbstractBinaryOperation {
    public Add(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left + right;
    }
}
