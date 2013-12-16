package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.grammar.pattern.AbstractComparableGrammarElement;
import java.util.List;

/**
 * This way of managing numbers is at least 2 times faster than
 * {@link AbstractOperand}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class NumberOperandGrammarElement<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    private static final char[] DIGITS = "0123456789".toCharArray();

    private boolean integer;

    public NumberOperandGrammarElement(final int priority,
            final boolean integer) {
        super(priority);
        this.integer = integer;
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = findFirstDigitIndex(carray, 0);
        if (start == -1) {
            return NOT_FOUND;
        }
        boolean point = false;
        boolean exp = false;
        int end = carray.length;
        FOR: for (int i=start + 1; i<carray.length; i++) {
            char c = carray[i];
            if (!isDigit(c)) {
                if (integer) {
                    break FOR;
                }
                switch (c) {
                    case '.':
                        if (point) {
                            return NOT_FOUND;
                        }
                        break;

                    case 'E':
                    case 'e':
                        if (exp) {
                            return NOT_FOUND;
                        }
                        break;

                    case '+':
                    case '-':
                        final char prevc = carray[i-1];
                        if (!(prevc == 'e' || prevc == 'E')) {
                            end = i;
                            break FOR;
                        }
                        break;

                    default:
                        end = i;
                        break FOR;
                }
            }
        }
        if (start > 0 &&
                isPreceededByAnOperatorOrByAParentheses(carray, start)) {
            start --;
        }
        return new InnerGrammarElementMatcher(start, end);
    }

    /**
     * This method here is controversial. It is needed to see if the
     * preceeding + or - should be part of the number, but this can be
     * determined only in a standard arithmetic with some checks. I just
     * assume here that the grammar defined is somewhat 'close' to the
     * standard arithmetic.
     */
    private boolean isPreceededByAnOperatorOrByAParentheses(
            final char[] carray,
            final int start) {
        final char signum = carray[start - 1];
        if (signum == '+' || signum == '-') {
            for (int j = start - 2; j >= 0; j--) {
                final char cj = carray[j];
                if (cj == '*' || cj == '/' || cj == '(' || cj == '+' ||
                        cj == '-' || cj == '^') {
                    return true;
                }
            }
        }
        return false;
    }

    private int findFirstDigitIndex(final char[] carray, final int start) {
        for (int i=start; i<carray.length; i++) {
            if (isDigit(carray[i])) {
                return i;
            }
        }
        return -1;
    }

    private boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    @SuppressWarnings("unchecked")
    public T evaluate(final String value, List<T> params, C context) {
        return (T) Double.valueOf(value);
    }

    @Override
    public int getRequiredOperandsAfter() {
        return 0;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return 0;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
