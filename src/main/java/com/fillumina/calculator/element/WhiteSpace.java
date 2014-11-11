package com.fillumina.calculator.element;

import com.fillumina.calculator.EvaluationException;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import java.util.List;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpace<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    private static final WhiteSpace<?,?> INSTANCE =
            new WhiteSpace<>(0, " \t\n");

    private final char[] whitespaces;

    @SuppressWarnings("unchecked")
    public static <T,C> WhiteSpace<T,C> instance() {
        return (WhiteSpace<T, C>) INSTANCE;
    }

    /**
     * @see VeryFastWhiteSpace
     *
     * @param whitespaces insert a string with the whitespace characters to
     *                    recognize. i.e.: " \t\n"
     * @param priority    the priority
     */
    public WhiteSpace(final int priority, final String whitespaces) {
        super(priority);
        this.whitespaces = whitespaces.toCharArray();
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();
        int start = -1, end = carray.length;
        char c;
        boolean isWhiteSpace = false;
        FOR: for (int i=0; i<carray.length; i++) {
            c = carray[i];
            for (int j=0; j<whitespaces.length; j++) {
                if (c == whitespaces[j]) {
                    isWhiteSpace = true;
                    break;
                }
            }
            if (isWhiteSpace) {
                if (start == -1) {
                    start = i;
                }
                break;
            } else {
                end = i;
                break FOR;
            }
        }
        if (start == -1) {
            return ElementMatcher.NOT_FOUND;
        }
        return new ElementMatcher(start, end);
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
}
