package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementType;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastMultiConstant<T,C>
        extends AbstractFastMultiGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final T value;

    public FastMultiConstant(final T value,
            final int priority,
            final String... symbols) {
        super(priority, 0, 0, symbols);
        this.value = value;
    }

    @Override
    public T evaluate(final String value, final List<T> params, final C context) {
        return this.value;
    }

    @Override
    protected boolean isOperator() {
        return false;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
