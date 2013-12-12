package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Defines the symbols that open a parenthesis. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * accepeted.
 * <br />
 * This class is also used internally as the parameters holder when a parenthesis
 * is discovered.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OpenParenthesis<T,C> extends UnevaluablePatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final OpenParenthesis<?,?> ROUND =
            new OpenParenthesis<>("\\(");

    public static final OpenParenthesis<?,?> ROUND_SQUARE =
            new OpenParenthesis<>("\\(\\[");

    public static final OpenParenthesis<?,?> ROUND_SQUARE_CURLY =
            new OpenParenthesis<>("\\(\\[\\{");

    public OpenParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPEN_PAR;
    }
}
