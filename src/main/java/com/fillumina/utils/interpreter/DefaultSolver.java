package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.EvaluableGrammarElement;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
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
        final GrammarElement grammarElement = node.getGrammarElement();
        if (!(grammarElement instanceof EvaluableGrammarElement<?,?>)) {
            throw new EvaluationException(node.getValue());
        }

        try {
            return ((EvaluableGrammarElement<T,C>)grammarElement)
                    .evaluate(node, params, context);
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new EvaluationException(node.getValue());
        }
    }
}
