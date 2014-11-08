package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import static java.lang.Character.isWhitespace;

/**
 * Extracts unquoted strings.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractStringOperand<T,C>
        extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractStringOperand(int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = -1;
        boolean escape = false;
        final int length = carray.length;
        for (int i=0; i<length; i++) {
            final char c = carray[i];
            if (escape) {
                escape = false;
            } else if (c == '\\') {
                escape = true;
            } else if (start == -1 && !isWhitespace(c)) {
                start = i;
            } else if (start != -1) {
                if (isWhitespace(c)) {
                    return new ElementMatcher(start, i);
                } else if (i == length -1) {
                    return new ElementMatcher(start, i + 1);
                }
            }
        }
        return ElementMatcher.NOT_FOUND;
    }

    protected static String evaluateString(final String value) {
        final char[] target = new char[value.length()];
        int targetIndex = 0;
        boolean escape = false;
        final char[] carray = value.toCharArray();
        for (int i=0; i<carray.length; i++) {
            final char c = carray[i];
            if (c == '\\' && !escape) {
                escape = true;
            } else {
                escape = false;
                target[targetIndex++] = c;
            }
        }
        return new String(target, 0, targetIndex);
    }
}
