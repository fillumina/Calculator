package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.List;

/**
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultSolver extends AbstractSolver
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final DefaultSolver INSTANCE = new DefaultSolver();

    @Override
    @SuppressWarnings("unchecked")
    public <T, C> T evaluate(final Node<T,C> node,
            final List<T> params, final C context) {
        final GrammarElement<T,C> grammarElement = node.getGrammarElement();

        try {
            return grammarElement.evaluate(node.getExpression(), params, context);
        } catch (Exception e) {
            throw new EvaluationException(node.getExpression(), e);
        }
    }
}
