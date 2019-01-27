package expression.parser;

import expression.*;
import expression.binaryOperation.*;
import expression.unaryOperation.*;
import expression.exceptions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;
    private Token currentToken;

    private Map<Token, String> map = new HashMap<Token, String>() {{
        put(Token.ADD, "add");
        put(Token.SUB, "subtract");
        put(Token.MUL, "multiply");
        put(Token.DIV, "divide");
        put(Token.VAR, "variable");
        put(Token.CONST, "const");
        put(Token.OPEN_BRACKET, "open bracket");
        put(Token.CLOSE_BRACKET, "close bracket");
        put(Token.UNARY_MINUS, "unary minus");
        put(Token.UNARY_PLUS, "unary plus");
        put(Token.END, "end of expression");
        put(Token.AND, "and");
        put(Token.XOR, "xor");
        put(Token.OR, "or");
        put(Token.NEGATE, "negate");
    }};

    private Set<Token> operations = new HashSet<Token>() {{
        add(Token.MUL);
        add(Token.DIV);
        add(Token.ADD);
        add(Token.SUB);
        add(Token.AND);
        add(Token.XOR);
        add(Token.OR);
    }};

    private Set<Token> unary = new HashSet<Token>() {{
        add(Token.VAR);
        add(Token.CONST);
        add(Token.OPEN_BRACKET);
        add(Token.UNARY_MINUS);
    }};

    private int balance;

    private void nextToken() throws ParsingException {
        currentToken = tokenizer.nextToken();
    }

    private TripleExpression parseUnary() throws ParsingException {
        Token prevToken = currentToken;
        nextToken();

        if (operations.contains(prevToken) && !unary.contains(currentToken)) {
            throw new MissingOperandException("Expected operand. Find " + map.get(currentToken));
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
                if (currentToken == Token.CLOSE_BRACKET) {
                    balance--;
                }
                nextToken();
                break;
            case NEGATE:
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
                if (currentToken == Token.NULL) {
                    throw new IndefiniteToken("Unknown token");
                } else {
                    throw new IndefiniteToken("Expected another token. Find " + map.get(currentToken));
                }
        }
        return result;
    }

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
        while (currentToken == Token.AND) {
            result = new And(result, parseAddSubtract());
        }
        return result;
    }

    private TripleExpression parseXor() throws ParsingException {
        TripleExpression result = parseAnd();
        while (currentToken == Token.XOR) {
            result = new Xor(result, parseAnd());
        }
        return result;
    }

    private TripleExpression parseOr() throws ParsingException {
        TripleExpression result = parseXor();
        while (currentToken == Token.OR) {
            result = new Or(result, parseXor());
        }
        return result;
    }

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        balance = 0;
        tokenizer = new Tokenizer(expression);

        TripleExpression result = parseOr();

        if (currentToken == Token.NULL) {
            throw new IndefiniteToken("Unknown token");
        }

        if (currentToken != Token.END) {
            throw new MissingOperatorException("Expected operator. Find " + map.get(currentToken));
        }

        if (balance > 0) {
            throw new MissingCloseBracketException("Missed " + balance + " close bracket");
        } else if (balance < 0) {
            throw new MissingOpenBracketException("Missed " + -balance + " open bracket");
        }
        return result;
    }
}
