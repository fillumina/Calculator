package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.AbstractComparableGrammarElement;
import com.fillumina.utils.interpreter.grammar.pattern.AbstractOperand;
import java.util.List;

/**
 * This way of managing numbers is at least 2 times faster than
 * {@link AbstractOperand}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastIntegerGrammarElement<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final FastIntegerGrammarElement<?,?> INSTANCE =
            new FastIntegerGrammarElement<>(0);

    public FastIntegerGrammarElement(final int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = findFirstDigitIndex(carray, 0);
        if (start == -1) {
            return FastGrammarElementMatcher.NOT_FOUND;
        }
        final int length = carray.length;
        int end = length;
        FOR: for (int i=start + 1; i<length; i++) {
            char c = carray[i];
            if (!isDigit(c)) {
                end = i;
                break FOR;
            }
        }
        if (start > 0 &&
                isPreceededByASignumAndAnOperatorOrParentheses(carray, start)) {
            start --; // includes the signum
        }
        return new FastGrammarElementMatcher(start, end);
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
