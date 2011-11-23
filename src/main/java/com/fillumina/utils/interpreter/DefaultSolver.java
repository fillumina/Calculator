package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author fra
 */
public class DefaultSolver<T,C> extends AbstractSolver<T,C> {

    @Override
    @SuppressWarnings("unchecked")
    public T evaluate(final Node node, final List<T> params, final C context) {
        final GrammarElement<T,C> grammarElement = node.getGrammarElement();

        try {
            return grammarElement.evaluate(node.getValue(), params, context);
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new EvaluationException(node.getValue());
        }
    }

}
