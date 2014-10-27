package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.grammar.GrammarElementType;

/**
 * Extracts strings surrounded by single or double quotes.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractQuotedStringFastElement<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractQuotedStringFastElement(int priority) {
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
                    return new FastElementMatcher(start, i + 1);
                }
            }
        }
        return FastElementMatcher.NOT_FOUND;
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
