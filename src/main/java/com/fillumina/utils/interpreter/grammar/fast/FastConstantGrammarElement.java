package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementType;
import java.util.List;

/**
 * Faster than {@link ConstantElement} but it cannot use REGEXP.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastConstantGrammarElement<T,C>
        extends AbstractFastGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    /** Insert the name of the constant NOT the regular expression. */
    public FastConstantGrammarElement(final String name, final T constant,
            final int priority) {
        super(name, priority, 0, 0);
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
