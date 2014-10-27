package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.grammar.AbstractComparableGrammarElement;
import com.fillumina.interpreter.EvaluationException;
import com.fillumina.interpreter.GrammarElementMatcher;
import com.fillumina.interpreter.grammar.GrammarElementType;
import java.util.List;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VeryFastWhiteSpace<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final VeryFastWhiteSpace<?,?> INSTANCE =
            new VeryFastWhiteSpace<>(0);

    public VeryFastWhiteSpace(final int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = -1, end = carray.length;
        for (int i=0; i<carray.length; i++) {
            final char c = carray[i];
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                if (start == -1) {
                    start = i;
                }
            } else {
                if (start != -1) {
                    end = i;
                    break;
                }
            }
        }
        if (start == -1) {
            return FastElementMatcher.NOT_FOUND;
        }
        return new FastElementMatcher(start, end);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.WHITE_SPACE;
    }

    @Override
    public T evaluate(final String value, final List<T> params,
            final C context) {
        throw new EvaluationException("Element not evaluable: " + value +
                ", parameters: " + params);
    }

    @Override
    public int getRequiredOperandsAfter() {
        return 0;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return 0;
    }
}
