package expression;

public class Variable implements TripleExpression {
    private String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (varName) {
            case "x": return x;
            case "y": return y;
            case "z": return z;
            default: return 0; //TODO throw exception
        }
    }
}
