package expression;

public class Variable implements Expression {
    private String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }
}
