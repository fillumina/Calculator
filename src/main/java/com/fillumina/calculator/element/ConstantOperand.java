package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementType;
import java.util.List;

/**
 * Represents a constant that has a fixed value.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ConstantOperand<T,C>
        extends AbstractElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    /** Insert the symbol of the constant NOT the regular expression. */
    public ConstantOperand(final String symbol, final T constant,
            final int priority) {
        super(symbol, priority, 0, 0);
        this.constant = constant;
    }

    @Override
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return constant;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }

    @Override
    protected boolean isOperator() {
        return false;
    }
}
