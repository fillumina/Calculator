package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractDoubleOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoubleOperand extends AbstractDoubleOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final DoubleOperand INSTANCE =
            new DoubleOperand();

    public DoubleOperand() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return Double.valueOf(value);
    }
}
