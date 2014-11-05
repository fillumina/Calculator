package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.AbstractComparableGrammarOperand;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractComparableGrammarOperand<T,C> {
    private static final long serialVersionUID = 1L;

    /**
     * @param priority  the priority at which this operand is evaluated
     *                  (usually 0)
     */
    public AbstractOperand(int priority) {
        super(priority);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
