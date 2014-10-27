package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.grammar.GrammarElementType;

/**
 * Defines the symbol that close a parenthesis. It's about 20% faster than
 * {@link CloseParentheses}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastCloseParentheses<T,C>
        extends UnevaluableFastElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final FastCloseParentheses<?,?> ROUND =
            new FastCloseParentheses<>(")");

    /** Only one symbol can be specified. */
    public FastCloseParentheses(final String symbol) {
        super(symbol, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.CLOSED_PAR;
    }
}
