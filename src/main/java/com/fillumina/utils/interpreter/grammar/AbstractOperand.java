package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElementType;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractOperand(final String symbolRegexp, final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return evaluate(value, context);
    }

    public abstract T evaluate(final String value, final C context);

    @Override
    public boolean isType(final GrammarElementType type) {
        return GrammarElementType.OPERAND.equals(type);
    }

}
