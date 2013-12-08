package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ConstantElement<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    public ConstantElement(final String symbolRegexp, final T constant,
            final int priority) {
        super(symbolRegexp, priority);
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
