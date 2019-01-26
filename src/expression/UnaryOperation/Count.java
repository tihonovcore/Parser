package expression.UnaryOperation;

import expression.TripleExpression;
import expression.exceptions.EvaluatingException;

public class Count extends AbstractUnaryOperation {
    public Count(TripleExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return Integer.bitCount(super.expression.evaluate(x, y, z));
    }
}
