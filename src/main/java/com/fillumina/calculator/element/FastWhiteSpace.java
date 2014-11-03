package com.fillumina.calculator.element;

import com.fillumina.calculator.EvaluationException;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.AbstractComparableGrammarOperand;
import java.util.List;

/**
 * Ignores common white space characters (CR,LF,TAB,SPACE).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastWhiteSpace<T,C>
        extends AbstractComparableGrammarOperand<T,C> {
    private static final long serialVersionUID = 1L;

    private static final FastWhiteSpace<?,?> INSTANCE =
            new FastWhiteSpace<>(0);

    @SuppressWarnings("unchecked")
    public static <T,C> FastWhiteSpace<T,C> instance() {
        return (FastWhiteSpace<T, C>) INSTANCE;
    }

    public FastWhiteSpace(final int priority) {
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
