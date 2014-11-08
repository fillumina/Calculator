package com.fillumina.calculator.grammar;

/**
 * Priority is used when building the solution tree to put high priority
 * elements first. This abstract class provides a simple priority mechanism.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractComparableGrammarOperand<T, C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;
    private static final String ERROR_MESSAGE = "Not supported.";

    /** Higher number is higher priority. */
    public AbstractComparableGrammarOperand(final int priority) {
        super(priority);
    }

    @Override
    public int getRequiredOperandsAfter() {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int getRequiredOperandsBefore() {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
