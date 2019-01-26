package expression.parser;

import expression.*;
import expression.BinaryOperation.*;
import expression.UnaryOperation.*;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;
    private Tokenizer.Token currentToken;

    private void nextToken() {
        currentToken = tokenizer.nextToken();
    }

    private TripleExpression parseUnary() {
        nextToken();

        TripleExpression result;
        switch (currentToken) {
            case CONST:
                result = new Const(tokenizer.getValue());
                nextToken();
                break;
            case VAR:
                result = new Variable(tokenizer.getVarName());
                nextToken();
                break;
            case OPEN_BRACKET:
                result = parseOr();
                nextToken();
                break;
            case NOT:
                result = new Not(parseUnary());
                break;
            case COUNT:
                result = new Count(parseUnary());
                break;
            case UNARY_PLUS:
                result = parseUnary();
                break;
            case UNARY_MINUS:
                result = new CheckedNegate(parseUnary());
                break;
            default:
//                TODO throw exception
                result = null;
        }

        return result;
    }

    //    TODO (expr)(expr) - is valid?
    private TripleExpression parseDivideMultiply() {
        TripleExpression result = parseUnary();
        while (true) {
            switch (currentToken) {
                case MUL:
                    result = new CheckedMultiply(result, parseUnary());
                    break;
                case DIV:
                    result = new CheckedDivide(result, parseUnary());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression parseAddSubtract() {
        TripleExpression result = parseDivideMultiply();
        while (true) {
            switch (currentToken) {
                case ADD:
                    result = new CheckedAdd(result, parseDivideMultiply());
                    break;
                case SUB:
                    result = new CheckedSubtract(result, parseDivideMultiply());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression parseAnd() {
        TripleExpression result = parseAddSubtract();
        while (currentToken == Tokenizer.Token.AND) {
            result = new And(result, parseAddSubtract());
        }
        return result;
    }

    private TripleExpression parseXor() {
        TripleExpression result = parseAnd();
        while (currentToken == Tokenizer.Token.XOR) {
            result = new Xor(result, parseAnd());
        }
        return result;
    }

    private TripleExpression parseOr() {
        TripleExpression result = parseXor();
        while (currentToken == Tokenizer.Token.OR) {
            result = new Or(result, parseXor());
        }
        return result;
    }

    @Override
    public TripleExpression parse(String expression) {
        tokenizer = new Tokenizer(expression);
        return parseOr();
    }
}
