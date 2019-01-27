package expression.unaryOperation;

import expression.TripleExpression;

public abstract class AbstractUnaryOperation implements TripleExpression {
    protected TripleExpression expression;

    AbstractUnaryOperation(TripleExpression expression) {
        this.expression = expression;
    }
}
