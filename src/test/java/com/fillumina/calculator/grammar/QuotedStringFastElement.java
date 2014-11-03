package com.fillumina.calculator.grammar;

import com.fillumina.calculator.grammar.element.AbstractQuotedStringOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class QuotedStringFastElement
        extends AbstractQuotedStringOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final QuotedStringFastElement INSTANCE =
            new QuotedStringFastElement();

    public QuotedStringFastElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return null;
    }
}
