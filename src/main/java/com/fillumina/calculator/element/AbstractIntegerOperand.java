package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import static com.fillumina.calculator.element.CharacterUtil.isDigit;

/**
 * Recognizes integers.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractIntegerOperand<T,C> extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractIntegerOperand(final int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = findFirstDigitIndex(carray, 0);
        if (start == -1) {
            return ElementMatcher.NOT_FOUND;
        }
        final int length = carray.length;
        int end = length;
        for (int i=start + 1; i<length; i++) {
            char c = carray[i];
            if (!isDigit(c)) {
                end = i;
                break;
            }
        }
        if (start > 0 &&
                isPreceededByASignumAndAnOperatorOrParentheses(carray, start)) {
            start --; // includes the signum
        }
        return new ElementMatcher(start, end);
    }

    /**
     * This method here is controversial. It is needed to see if the
     * preceeding + or - should be part of the number, but this can be
     * determined only in a standard arithmetic with some checks. I just
     * assume here that the grammar defined is somewhat 'close' to the
     * standard arithmetic.
     */
    private boolean isPreceededByASignumAndAnOperatorOrParentheses(
            final char[] carray,
            final int start) {
        final char signum = carray[start - 1];
        if (signum == '+' || signum == '-') {
            for (int j = start - 2; j >= 0; j--) {
                final char cj = carray[j];
                if (cj == '*' || cj == '/' || cj == '(' || cj == '+' ||
                        cj == '-' || cj == '^') {
                    return true;
                } else if (cj != ' ' && cj != '\n' && cj != '\t') {
                    return false;
                }
            }
            return true;
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
}
