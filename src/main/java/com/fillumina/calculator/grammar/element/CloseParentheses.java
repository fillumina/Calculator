package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.grammar.GrammarElementType;

/**
 * Defines the symbol that close a parenthesis. It's about 20% faster than
 * {@link CloseParentheses}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CloseParentheses<T,C>
        extends UnevaluableElement<T,C> {
    private static final long serialVersionUID = 1L;

    private static final CloseParentheses<?,?> ROUND =
            new CloseParentheses<>(")");

    @SuppressWarnings("unchecked")
    public static <T,C> CloseParentheses<T,C> round() {
        return (CloseParentheses<T, C>) ROUND;
    }

    /** Only one symbol can be specified. */
    public CloseParentheses(final String symbol) {
        super(symbol, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.CLOSED_PAR;
    }
}
