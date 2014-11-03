package com.fillumina.calculator.pattern.test;

import com.fillumina.calculator.pattern.AbstractOperand;
import java.util.Map;

/**
 * Used mainly in tests to return elements as they are without further
 * processing.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestContextOperand
        extends AbstractOperand<String, Map<String,String>> {
    private static final long serialVersionUID = 1L;

    public TestContextOperand(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public String evaluate(final String value, final Map<String,String> context) {
        return value;
    }
}
