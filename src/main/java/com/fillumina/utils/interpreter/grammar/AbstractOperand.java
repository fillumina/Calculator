package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElement.Type;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractEvaluableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractOperand(final String symbolRegexp, final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T eval(final String value,
            final List<T> params, final C context) {
        return eval(value, context);
    }

    public abstract T eval(final String value, final C context);

    @Override
    public boolean isType(final Type type) {
        return Type.OPERAND.equals(type);
    }

}
