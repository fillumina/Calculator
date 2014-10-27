package com.fillumina.interpreter.grammar.pattern;

import com.fillumina.interpreter.grammar.GrammarElementType;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ConstantElement<T,C>
        extends AbstractPatternElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    /** Insert the name of the constant NOT the regular expression. */
    public ConstantElement(final String name, final T constant,
            final int priority) {
        super("^(" + name + ")$", priority);
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
}
