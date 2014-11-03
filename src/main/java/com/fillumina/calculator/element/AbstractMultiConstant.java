package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;

/**
 * Defines a constant.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractMultiConstant<T,C>
        extends AbstractMultiMatchingElement<T, C> {
    private static final long serialVersionUID = 1L;

    public AbstractMultiConstant(final int priority,
            final String... symbols) {
        super(priority, 0, 0, symbols);
    }

    @Override
    protected boolean isOperator() {
        return false;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
