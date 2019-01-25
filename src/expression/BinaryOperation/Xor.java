package expression.BinaryOperation;

import expression.TripleExpression;

public class Xor extends AbstractBinaryOperation {
    public Xor(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    @Override
    protected int result(int left, int right) {
        return left ^ right;
    }
}
