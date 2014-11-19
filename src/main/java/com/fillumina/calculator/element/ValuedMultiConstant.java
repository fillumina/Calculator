package com.fillumina.calculator.element;

import java.util.List;

/**
 * Allows to specify the value of the constant with many different symbols.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ValuedMultiConstant<T,C>
        extends AbstractMultiConstant<T, C> {
    private static final long serialVersionUID = 1L;

    private final T value;

    public ValuedMultiConstant(final T value,
            final int priority,
            final String... symbols) {
        super(priority, symbols);
        this.value = value;
    }

    @Override
    public T evaluate(final String value, final List<T> params, final C context) {
        return this.value;
    }
}
