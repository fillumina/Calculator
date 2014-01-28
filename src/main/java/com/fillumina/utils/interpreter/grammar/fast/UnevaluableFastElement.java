package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.EvaluationException;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class UnevaluableFastElement<T, C>
        extends AbstractFastGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    public UnevaluableFastElement(final String symbol,
            final int priority) {
        super(symbol, priority, 0, 0);
    }

    @Override
    protected boolean isOperator() {
        return true;
    }

    @Override
    public T evaluate(final String value, final List<T> params,
            final C context) {
        throw new EvaluationException("Element not evaluable: " + value +
                ", parameters: " + params);
    }
}
