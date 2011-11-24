package com.fillumina.utils.interpreter.grammar;

import java.util.List;

/**
 * Is an element that can be evaluated to give a result. It is almost
 * never needed to use this class directly, use one of its derived classes
 * instead.
 *
 * @see AbstractOperand
 * @see AbstractOperator
 * @see AbstractUnrecognizedElement
 * @see ConstantGrammarElement
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author fra
 */
public abstract class AbstractEvaluableGrammarElement<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractEvaluableGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return eval(value, params, context);
    }

    public abstract T eval(String value,
            List<T> params, C context);

}
