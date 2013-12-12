package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Defines the symbols that close a parenthesis. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * accepted.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CloseParenthesis<T,C> extends UnevaluablePatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final CloseParenthesis<?,?> ROUND =
            new CloseParenthesis<>("\\)");

    public static final CloseParenthesis<?,?> ROUND_SQUARE =
            new CloseParenthesis<>("\\)\\]");

    public static final CloseParenthesis<?,?> ROUND_SQUARE_CURLY =
            new CloseParenthesis<>("\\)\\]\\}");

    public CloseParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.CLOSED_PAR;
    }
}
