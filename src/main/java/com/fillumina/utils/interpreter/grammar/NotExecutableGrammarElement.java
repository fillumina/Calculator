package com.fillumina.utils.interpreter.grammar;

/**
 *
 * @author fra
 */
public class NotExecutableGrammarElement extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public NotExecutableGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

}
