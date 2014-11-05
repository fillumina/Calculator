package com.fillumina.calculator.grammar;

import com.fillumina.calculator.element.AbstractDateOperand;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DateFastOperand extends AbstractDateOperand<Double,Void> {
    private static final long serialVersionUID = 1L;

    public static final DateFastOperand DATE =
            new DateFastOperand("dd/MM/yy");
    public static final DateFastOperand DATE_TIME =
            new DateFastOperand("dd/MM/yy HH:mm:ss");

    public DateFastOperand(final String pattern) {
        super(0, pattern);
    }

    @Override
    public Double evaluate(final String val, final List<Double> params,
            Void context) {
        return null;
    }
}
