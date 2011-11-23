package com.fillumina.utils.interpreter.grammar;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author fra
 */
public class WhiteSpace extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public WhiteSpace(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

}
