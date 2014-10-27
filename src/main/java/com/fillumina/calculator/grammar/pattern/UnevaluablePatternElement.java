package com.fillumina.calculator.grammar.pattern;

import com.fillumina.calculator.EvaluationException;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class UnevaluablePatternElement<T,C>
        extends AbstractPatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public UnevaluablePatternElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T evaluate(final String value, final List<T> params, final C context) {
        throw new EvaluationException("Element not evaluable: " + value +
                ", parameters: " + params);
    }
}
