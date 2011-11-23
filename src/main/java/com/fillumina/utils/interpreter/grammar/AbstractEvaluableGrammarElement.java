package com.fillumina.utils.interpreter.grammar;

/**
 * Is an element that can be evaluated to give a result.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author fra
 */
public abstract class AbstractEvaluableGrammarElement<T,C>
        extends PatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractEvaluableGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

}
