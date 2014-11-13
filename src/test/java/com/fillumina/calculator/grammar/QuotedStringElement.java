package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractQuotedStringOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class QuotedStringElement
        extends AbstractQuotedStringOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final QuotedStringElement INSTANCE =
            new QuotedStringElement();

    public QuotedStringElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return null;
    }
}
