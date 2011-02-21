package com.fillumina.utils.interpreter.grammar;

/**
 *
 * @author fra
 */
public class WhiteSpace extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public WhiteSpace(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

}
