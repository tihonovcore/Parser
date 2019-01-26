package expression.parser;

import expression.*;
import expression.BinaryOperation.*;
import expression.UnaryOperation.*;
import expression.exceptions.*;

import java.util.HashSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;
    private Tokenizer.Token currentToken;

    private Set<Tokenizer.Token> operations = new HashSet<Tokenizer.Token>() {{
        add(Tokenizer.Token.ADD);
        add(Tokenizer.Token.OR);
        add(Tokenizer.Token.XOR);
        add(Tokenizer.Token.MUL);
        add(Tokenizer.Token.DIV);
        add(Tokenizer.Token.SUB);
        add(Tokenizer.Token.AND);
    }};

    private int balance;

    private void nextToken() throws ParsingException {
        currentToken = tokenizer.nextToken();
    }

    private TripleExpression parseUnary() throws ParsingException {
        Tokenizer.Token prevToken = currentToken;
        nextToken();

        if (operations.contains(prevToken) && (currentToken != Tokenizer.Token.VAR && currentToken != Tokenizer.Token.CONST && currentToken != Tokenizer.Token.OPEN_BRACKET && currentToken != Tokenizer.Token.UNARY_MINUS)) {
            throw new MissingOperandException("");
        }

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
                balance++;
                result = parseOr();
                if (currentToken == Tokenizer.Token.CLOSE_BRACKET) {
                    balance--;
                }
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
                throw new IndefiniteToken("");
        }

        if (balance < 0) {
            throw new MissingOpenBracketException(""); //TODO message
        }

        return result;
    }

    //    TODO (expr)(expr) - is valid?
    private TripleExpression parseDivideMultiply() throws ParsingException {
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

    private TripleExpression parseAddSubtract() throws ParsingException {
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

    private TripleExpression parseAnd() throws ParsingException {
        TripleExpression result = parseAddSubtract();
        while (currentToken == Tokenizer.Token.AND) {
            result = new And(result, parseAddSubtract());
        }
        return result;
    }

    private TripleExpression parseXor() throws ParsingException {
        TripleExpression result = parseAnd();
        while (currentToken == Tokenizer.Token.XOR) {
            result = new Xor(result, parseAnd());
        }
        return result;
    }

    private TripleExpression parseOr() throws ParsingException {
        TripleExpression result = parseXor();
        while (currentToken == Tokenizer.Token.OR) {
            result = new Or(result, parseXor());
        }
        return result;
    }

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        balance = 0;
        tokenizer = new Tokenizer(expression);

        TripleExpression result = parseOr();

        if (currentToken != Tokenizer.Token.END) {
            throw new ParsingException("");
        }
        if (balance != 0) {
            throw new MissingCloseBracketException("_parse");
        }
        return result;
    }
}
