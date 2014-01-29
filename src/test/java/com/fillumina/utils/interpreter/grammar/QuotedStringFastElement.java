package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.grammar.fast.AbstractQuotedStringFastElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class QuotedStringFastElement
        extends AbstractQuotedStringFastElement<Double,Void> {
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
