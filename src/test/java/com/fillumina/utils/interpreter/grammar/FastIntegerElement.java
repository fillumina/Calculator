package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.grammar.fast.AbstractIntegerFastElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastIntegerElement
        extends AbstractIntegerFastElement<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final FastIntegerElement INSTANCE =
            new FastIntegerElement();

    public FastIntegerElement() {
        super(0);
    }

    @Override
    public Double evaluate(final String value, final List<Double> params,
            Void context) {
        return Double.valueOf(value);
    }
}
