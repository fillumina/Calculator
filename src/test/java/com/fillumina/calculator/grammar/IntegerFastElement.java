package com.fillumina.calculator.grammar;

import com.fillumina.calculator.grammar.element.AbstractIntegerOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class IntegerFastElement
        extends AbstractIntegerOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final IntegerFastElement INSTANCE =
            new IntegerFastElement();

    public IntegerFastElement() {
        super(0);
    }

    @Override
    public Double evaluate(final String value, final List<Double> params,
            Void context) {
        return Double.valueOf(value);
    }
}
