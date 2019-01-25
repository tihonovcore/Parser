package expression.UnaryOperation;

import expression.TripleExpression;

public abstract class AbstractUnaryOperation implements TripleExpression {
    protected TripleExpression expression;

    public AbstractUnaryOperation(TripleExpression expression) {
        this.expression = expression;
    }
}
