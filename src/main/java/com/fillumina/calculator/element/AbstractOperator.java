package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;

/**
 * Base class for operators.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperator<T,C>
        extends AbstractElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractOperator(final int priority,
            final int operandsBefore,
            final int operandsAfter,
            final String symbol) {
        super(symbol, priority, operandsBefore, operandsAfter);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }

    @Override
    protected boolean isOperator() {
        return true;
    }
}
