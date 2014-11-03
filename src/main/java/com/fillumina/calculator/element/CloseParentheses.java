package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;

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
