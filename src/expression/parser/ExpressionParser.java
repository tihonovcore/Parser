package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    private String expression;

    private enum Token {ADD, SUB, MUL, DIV, VAR, CONST, OPEN_BRACKET, CLOSE_BRACKET, UNARY_PLUS, UNARY_MINUS, END}

    private Token currentToken;
    private String varName;
    private int index;
    private int value;

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void nextToken() {
        skipWhitespace();

        if (index >= expression.length()) {
            currentToken = Token.END;
            return;
        }

        char ch = expression.charAt(index);
        switch (ch) {
            case '+':
                if (currentToken == Token.VAR || currentToken == Token.CONST || currentToken == Token.CLOSE_BRACKET) {
                    currentToken = Token.ADD;
                } else {
                    currentToken = Token.UNARY_PLUS;
                }
                break;
            case '-':
                if (currentToken == Token.VAR || currentToken == Token.CONST || currentToken == Token.CLOSE_BRACKET) {
                    currentToken = Token.SUB;
                } else {
                    currentToken = Token.UNARY_MINUS;
                }
                break;
            case '*':
                currentToken = Token.MUL;
                break;
            case '/':
                currentToken = Token.DIV;
                break;
            case '(':
                currentToken = Token.OPEN_BRACKET;
                break;
            case ')':
                currentToken = Token.CLOSE_BRACKET;
                break;
            default:
                if (Character.isDigit(ch)) {
                    int start = index;
                    while (index + 1 < expression.length() && Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                    }

                    value = Integer.parseInt(expression.substring(start, index + 1));
                    currentToken = Token.CONST;
                } else if (Character.isLetter(ch)) {
                    varName = Character.toString(ch);
                    currentToken = Token.VAR;
                }
        }
        index++;
    }

//    TODO doesnt work with INT_MIN
    private TripleExpression parseUnary() {
        nextToken();

        TripleExpression result;
        switch (currentToken) {
            case CONST:
                result = new Const(value);
                nextToken();
                break;
            case VAR:
                result = new Variable(varName);
                nextToken();
                break;
            case OPEN_BRACKET:
                result = parseAddSubtract();
                nextToken();
                break;
            case UNARY_PLUS:
                result = parseUnary();
                break;
            case UNARY_MINUS:
                result = new Multiply(new Const(-1), parseUnary());
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
                    result = new Multiply(result, parseUnary());
                    break;
                case DIV:
                    result = new Divide(result, parseUnary());
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
                    result = new Add(result, parseDivideMultiply());
                    break;
                case SUB:
                    result = new Subtract(result, parseDivideMultiply());
                    break;
                default:
                    return result;
            }
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        this.expression = expression;
        index = 0;
        return parseAddSubtract();
    }
}
