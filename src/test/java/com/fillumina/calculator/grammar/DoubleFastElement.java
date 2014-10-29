package com.fillumina.calculator.grammar;

import com.fillumina.calculator.grammar.element.AbstractFastDoubleOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoubleFastElement extends AbstractFastDoubleOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final DoubleFastElement INSTANCE =
            new DoubleFastElement();

    public DoubleFastElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return Double.valueOf(value);
    }
}
