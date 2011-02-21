package com.fillumina.utils.interpreter.grammar;

/**
 *
 * @author fra
 */
public class OpenParenthesis extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public OpenParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

}
