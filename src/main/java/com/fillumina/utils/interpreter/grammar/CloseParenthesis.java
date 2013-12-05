package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Defines the symbols that close a parenthesis. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is all right.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CloseParenthesis<T,C> extends UnevaluablePatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public CloseParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public boolean isType(final GrammarElementType type) {
        return GrammarElementType.CLOSED_PAR.equals(type);
    }

}
