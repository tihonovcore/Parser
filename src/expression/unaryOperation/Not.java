package expression.unaryOperation;

import expression.TripleExpression;
import expression.exceptions.EvaluatingException;

public class Not extends AbstractUnaryOperation {
    public Not(TripleExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return ~expression.evaluate(x, y, z);
    }
}
