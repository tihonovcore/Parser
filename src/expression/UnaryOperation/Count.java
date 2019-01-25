package expression.UnaryOperation;

import expression.UnaryOperation.AbstractUnaryOperation;
import expression.TripleExpression;

public class Count extends AbstractUnaryOperation {
    public Count(TripleExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.bitCount(super.expression.evaluate(x, y, z));
    }
}
