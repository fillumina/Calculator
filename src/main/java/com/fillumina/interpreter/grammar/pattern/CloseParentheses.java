package com.fillumina.interpreter.grammar.pattern;

import com.fillumina.interpreter.grammar.GrammarElementType;

/**
 * Defines the symbols that close a parentheses. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * accepted.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CloseParentheses<T,C>
        extends UnevaluablePatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final CloseParentheses<?,?> ROUND =
            new CloseParentheses<>("\\)");

    public static final CloseParentheses<?,?> ROUND_SQUARE =
            new CloseParentheses<>("\\)\\]");

    public static final CloseParentheses<?,?> ROUND_SQUARE_CURLY =
            new CloseParentheses<>("\\)\\]\\}");

    public CloseParentheses(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.CLOSED_PAR;
    }
}
