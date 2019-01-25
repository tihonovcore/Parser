package expression.UnaryOperation;

import expression.TripleExpression;

public class Not extends AbstractUnaryOperation {
    public Not(TripleExpression expression) {
        super(expression);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return ~expression.evaluate(x, y, z);
    }
}
