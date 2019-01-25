package expression.parser;

class Tokenizer {
    private String expression;
    private Token currentToken;
    private String varName;
    private int index;
    private int value;

    enum Token {
        ADD, SUB, MUL, DIV, VAR, CONST, OPEN_BRACKET, CLOSE_BRACKET, UNARY_PLUS, UNARY_MINUS, END,
        AND, XOR, OR, COUNT, NOT
    }

    Tokenizer(String expression) {
        this.expression = expression;
    }

    int getValue() {
        return value;
    }

    String getVarName() {
        return varName;
    }

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    Token nextToken() {
        skipWhitespace();

        if (index >= expression.length()) {
            currentToken = Token.END;
            return currentToken;
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
            case '&':
                currentToken = Token.AND;
                break;
            case '^':
                currentToken = Token.XOR;
                break;
            case '|':
                currentToken = Token.OR;
                break;
            case '~':
                currentToken = Token.NOT;
                break;
            default:
                if (Character.isDigit(ch)) {
                    int start = index;
                    while (index + 1 < expression.length() && Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                    }

                    value = Integer.parseUnsignedInt(expression.substring(start, index + 1));
                    currentToken = Token.CONST;
                } else if (index + 4 < expression.length() && expression.substring(index, index + 5).equals("count")) {
                    index += 4;
                    currentToken = Token.COUNT;
                } else if (Character.isLetter(ch)) {
                    varName = Character.toString(ch);
                    currentToken = Token.VAR;
                }
        }
        index++;
        return currentToken;
    }
}