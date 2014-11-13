package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractStringOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringElement
        extends AbstractStringOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final StringElement INSTANCE =
            new StringElement();

    public StringElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return null;
    }
}
