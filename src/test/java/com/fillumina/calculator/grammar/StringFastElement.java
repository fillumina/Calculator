package com.fillumina.calculator.grammar;

import com.fillumina.calculator.grammar.fast.AbstractStringFastElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringFastElement
        extends AbstractStringFastElement<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final StringFastElement INSTANCE =
            new StringFastElement();

    public StringFastElement() {
        super(0);
    }

    @Override
    public Double evaluate(String value, List<Double> params, Void context) {
        return null;
    }
}
