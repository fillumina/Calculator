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
public class FastWhiteSpace<T,C> extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final FastWhiteSpace<?,?> INSTANCE =
            new FastWhiteSpace<>(" \t\n", 0);

    private final char[] whitespaces;

    /**
     * @see VeryFastWhiteSpace
     *
     * @param whitespaces insert a string with the whitespace characters to
     *                    recognize. i.e.: " \t\n"
     * @param priority    the priority
     */
    public FastWhiteSpace(final String whitespaces, final int priority) {
        super(priority);
        this.whitespaces = whitespaces.toCharArray();
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = -1, end = carray.length;
        FOR: for (int i=0; i<carray.length; i++) {
            final char c = carray[i];
            for (int j=0; j<whitespaces.length; j++) {
                if (c == whitespaces[j]) {
                    if (start == -1) {
                        start = i;
                    }
                    break;
                } else {
                    end = i;
                    break FOR;
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
