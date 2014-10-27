package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.grammar.GrammarElementType;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastMultiConstant<T,C>
        extends AbstractMultiFastElement<T, C> {
    private static final long serialVersionUID = 1L;

    public AbstractFastMultiConstant(final int priority,
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
