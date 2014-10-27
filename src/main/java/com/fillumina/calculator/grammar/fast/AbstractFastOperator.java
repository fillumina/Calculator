package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.grammar.GrammarElementType;

/**
 * Faster than {@link AbstractOperatorName} but it cannot use REGEXP.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastOperator<T,C>
        extends AbstractFastElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractFastOperator(final int priority,
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
