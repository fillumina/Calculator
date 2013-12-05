package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.EvaluationException;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class UnevaluablePatternGrammarElement<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public UnevaluablePatternGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T evaluate(final String value, final List<T> params, final C context) {
        throw new EvaluationException("Element not evaluable: " + value +
                ", parameters: " + params);
    }
}
