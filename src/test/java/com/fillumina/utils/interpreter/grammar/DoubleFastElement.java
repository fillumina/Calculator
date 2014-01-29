package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.grammar.fast.AbstractDoubleFastElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoubleFastElement extends AbstractDoubleFastElement<Double,Void> {
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