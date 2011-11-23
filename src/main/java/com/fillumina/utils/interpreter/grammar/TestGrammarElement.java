package com.fillumina.utils.interpreter.grammar;

/**
 * Used mainly in tests to return elements as they are without further
 * processing.
 *
 * @author fra
 */
public class TestGrammarElement extends PatternGrammarElement {
    private static final long serialVersionUID = 1L;

    public TestGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

}
