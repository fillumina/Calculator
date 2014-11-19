package com.fillumina.calculator;

import java.util.List;

/**
 * Evaluates an expression using its
 * {@link GrammarElement#evaluate(String, java.util.List, Object) } method.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultNodeEvaluator implements NodeEvaluator {
    private static final long serialVersionUID = 1L;

    public static final NodeEvaluator INSTANCE = new DefaultNodeEvaluator();

    private DefaultNodeEvaluator() {}

    @Override
    public <T, C> T evaluate(final Node<T,C> node,
            final List<T> params, final C context) {
        final GrammarElement<T,C> grammarElement = node.getGrammarElement();
        if (node.hasValue()) {
            return node.getValue();
        }
        try {
            return grammarElement.evaluate(node.getExpression(), params, context);
        } catch (SyntaxErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EvaluationException(node.getExpression(), ex);
        }
    }
}
