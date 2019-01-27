package expression.binaryOperation;

import expression.TripleExpression;

public class Or extends AbstractBinaryOperation {
    public Or(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left | right;
    }
}
