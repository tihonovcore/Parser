package expression;

public class Divide implements TripleExpression {
    private TripleExpression left;
    private TripleExpression right;

    public Divide(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) / right.evaluate(x, y, z);
    }
}
