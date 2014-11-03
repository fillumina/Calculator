package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.GrammarElementMatcher;

/**
 * Extracts strings surrounded by single or double quotes.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractQuotedStringOperand<T,C>
        extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractQuotedStringOperand(int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = -1;
        char quote = 0;
        boolean escape = false;
        final int length = carray.length;
        for (int i=0; i<length; i++) {
            final char c = carray[i];
            if (escape) {
                escape = false;
            } else if (c == '\\') {
                escape = true;
            } else if (c == '\'' || c == '\"') {
                if (quote == 0) {
                    start = i;
                    quote = c;
                } else if (c == quote) {
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
