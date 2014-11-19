package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;

/**
 * Matches an operator that can have multiple symbols (ie. AND, and, And, &&..).
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractMultiOperator<T,C>
        extends AbstractMultiMatchingElement<T, C> {
    private static final long serialVersionUID = 1L;

    public AbstractMultiOperator(final int priority,
            final int operandsBefore,
            final int operandsAfter,
            final String... symbols) {
        super(priority, operandsBefore, operandsAfter, symbols);
    }

    @Override
    protected boolean isOperator() {
        return true;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }
}
