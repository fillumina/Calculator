package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElement.Type;

/**
 * An operator is an evaluable element that expects some arguments
 * possibly before and/or after its symbol.
 * The argument's number defines only the maximum, the actual number
 * depends on the effective availability.
 *
 * @param <T> the base element type this operator work on
 * @param <C> the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperator<T,C>
        extends AbstractEvaluableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    private final int requiredOperandsBefore;
    private final int requiredOperandsAfter;

    public AbstractOperator(final String symbolRegexp,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(symbolRegexp, priority);
        this.requiredOperandsBefore = requiredOperandsBefore;
        this.requiredOperandsAfter = requiredOperandsAfter;
    }

    @Override
    public int getRequiredOperandsAfter() {
        return requiredOperandsAfter;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return requiredOperandsBefore;
    }

    @Override
    public boolean isType(final Type type) {
        return Type.OPERATOR.equals(type);
    }
}
