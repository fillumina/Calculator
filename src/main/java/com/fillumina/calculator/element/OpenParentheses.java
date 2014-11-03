package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;

/**
 * Defines the symbol that open a parenthesis. It's about 20% faster than
 * {@link OpenParentheses}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OpenParentheses<T,C>
        extends UnevaluableElement<T,C> {
    private static final long serialVersionUID = 1L;

    private static final OpenParentheses<?,?> ROUND =
            new OpenParentheses<>("(");

    @SuppressWarnings("unchecked")
    public static <T,C> OpenParentheses<T,C> round() {
        return (OpenParentheses<T, C>) ROUND;
    }

    /** Only one symbol can be specified. */
    public OpenParentheses(final String symbol) {
        super(symbol, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPEN_PAR;
    }
}
