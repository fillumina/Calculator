package com.fillumina.utils.interpreter.grammar;

/**
 * Defines the symbols that close a parenthesis. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is all right.
 *
 * @author fra
 */
public class CloseParenthesis extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public CloseParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

}
