package com.fillumina.interpreter.grammar.pattern;

import com.fillumina.interpreter.grammar.GrammarElementType;

/**
 * Defines the symbols that open a parentheses. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * accepeted.
 * <br />
 * This class is also used internally as the parameters holder when a
 * parentheses is discovered.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OpenParentheses<T,C>
        extends UnevaluablePatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final OpenParentheses<?,?> ROUND =
            new OpenParentheses<>("\\(");

    public static final OpenParentheses<?,?> ROUND_SQUARE =
            new OpenParentheses<>("\\(\\[");

    public static final OpenParentheses<?,?> ROUND_SQUARE_CURLY =
            new OpenParentheses<>("\\(\\[\\{");

    public OpenParentheses(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPEN_PAR;
    }
}
