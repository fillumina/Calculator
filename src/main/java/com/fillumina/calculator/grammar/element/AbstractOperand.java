package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import com.fillumina.calculator.grammar.GrammarElementType;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractComparableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractOperand() {
        this(0);
    }

    /**
     * @param priority  the priority at which this operand is evaluated
     *                  (usually 0)
     */
    public AbstractOperand(int priority) {
        super(priority);
    }

    @Override
    public int getRequiredOperandsAfter() {
        return 0;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return 0;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }

    protected static boolean isDigit(final char c) {
        return c <= '9' && c >= '0';
    }
}
