package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * Evaluates an expression using its
 * {@link GrammarElement#evaluate(String, java.util.List, Object) } method.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultEvaluator implements Evaluator {
    private static final long serialVersionUID = 1L;

    public static final Evaluator INSTANCE = new DefaultEvaluator();

    @Override
    public <T, C> T evaluate(final Node<T,C> node,
            final List<T> params, final C context) {
        final GrammarElement<T,C> grammarElement = node.getGrammarElement();
        try {
            return grammarElement.evaluate(node.getExpression(), params, context);
        } catch (SyntaxErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EvaluationException(node.getExpression(), ex);
        }
    }
}
