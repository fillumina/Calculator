package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractDoubleOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternElement
        extends AbstractDoubleOperand<Double, Void> {
    private static final long serialVersionUID = 1L;

    public static final DoublePatternElement INSTANCE =
            new DoublePatternElement();

    public DoublePatternElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return Double.valueOf(value);
    }
}
