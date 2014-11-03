package com.fillumina.calculator.grammar.pattern;

import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.element.AbstractPatternElement;
import java.util.List;

/**
 * Base class for operands. Remember that operands should have the
 * higher priority in the grammar and so they should be defined first.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractPatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractOperand(final String symbolRegexp, final int priority) {
        super(priority, symbolRegexp);
    }

    /** And operand doesn't have parameters. */
    @Override
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return evaluate(value, context);
    }

    public abstract T evaluate(final String value, final C context);

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
}
