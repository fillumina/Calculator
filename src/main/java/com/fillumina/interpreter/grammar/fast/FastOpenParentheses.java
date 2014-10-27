package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.grammar.GrammarElementType;

/**
 * Defines the symbol that open a parenthesis. It's about 20% faster than
 * {@link OpenParentheses}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastOpenParentheses<T,C>
        extends UnevaluableFastElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final FastOpenParentheses<?,?> ROUND =
            new FastOpenParentheses<>("(");

    /** Only one symbol can be specified. */
    public FastOpenParentheses(final String symbol) {
        super(symbol, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPEN_PAR;
    }
}
