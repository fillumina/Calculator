package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.grammar.fast.AbstractIntegerFastElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class IntegerFastElement
        extends AbstractIntegerFastElement<Double,Void> {
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
