package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Defines the symbol that open a parenthesis. It's about 20% faster than
 * {@link OpenParentheses}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastOpenParentheses<T,C>
        extends UnevaluableFastGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final FastOpenParentheses<?,?> ROUND =
            new FastOpenParentheses<>("(");

    public FastOpenParentheses(final String name) {
        super(name, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPEN_PAR;
    }
}
