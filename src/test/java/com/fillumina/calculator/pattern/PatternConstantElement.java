package com.fillumina.calculator.pattern;

import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.element.AbstractPatternElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternConstantElement<T,C>
        extends AbstractPatternElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    /** Insert the name of the constant NOT the regular expression. */
    public PatternConstantElement(final String name, final T constant,
            final int priority) {
        super(priority, "^(" + name + ")$");
        this.constant = constant;
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
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return constant;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}