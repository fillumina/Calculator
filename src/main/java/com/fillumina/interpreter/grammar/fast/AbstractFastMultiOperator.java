package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.grammar.GrammarElementType;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastMultiOperator<T,C>
        extends AbstractMultiFastElement<T, C> {
    private static final long serialVersionUID = 1L;

    public AbstractFastMultiOperator(final int priority,
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
