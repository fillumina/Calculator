package com.fillumina.calculator.grammar;

import com.fillumina.calculator.pattern.AbstractDoublePatternElement;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternElement
        extends AbstractDoublePatternElement<Double, Void> {
    private static final long serialVersionUID = 1L;

    public static final DoublePatternElement INSTANCE =
            new DoublePatternElement();

    public DoublePatternElement() {
        super(0);
    }

    @Override
    public Double evaluate(final String value, Void context) {
        return Double.valueOf(value);
    }
}
