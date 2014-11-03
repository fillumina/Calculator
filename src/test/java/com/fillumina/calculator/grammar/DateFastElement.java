package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractDateOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DateFastElement extends AbstractDateOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final DateFastElement DATE =
            new DateFastElement("dd/MM/yy");
    public static final DateFastElement DATE_TIME =
            new DateFastElement("dd/MM/yy HH:mm:ss");

    public DateFastElement(final String pattern) {
        super(0, pattern);
    }

    @Override
    public Double evaluate(final String val, final List<Double> params,
            Void context) {
        return null;
    }
}
