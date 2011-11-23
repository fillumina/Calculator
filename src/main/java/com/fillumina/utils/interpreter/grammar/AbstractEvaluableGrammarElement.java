package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.Node;
import java.util.List;

/**
 * Is an element that can be evaluated to give a result.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author fra
 */
public abstract class AbstractEvaluableGrammarElement<T,C> extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public AbstractEvaluableGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    public abstract T evaluate(final Node node, final List<T> params,
            final C context);

}
