package com.fillumina.calculator.pattern;

import com.fillumina.calculator.GrammarElementType;

/**
 * Defines the symbols that close a parentheses. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * accepted.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternCloseParentheses<T,C>
        extends UnevaluablePatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final PatternCloseParentheses<?,?> ROUND =
            new PatternCloseParentheses<>("\\)");

    public static final PatternCloseParentheses<?,?> ROUND_SQUARE =
            new PatternCloseParentheses<>("\\)\\]");

    public static final PatternCloseParentheses<?,?> ROUND_SQUARE_CURLY =
            new PatternCloseParentheses<>("\\)\\]\\}");

    public PatternCloseParentheses(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.CLOSED_PAR;
    }
}
