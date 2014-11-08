package com.fillumina.calculator.pattern.test;

import com.fillumina.calculator.pattern.AbstractPatternOperand;

/**
 * Used mainly in tests to return elements as they are without further
 * processing.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestOperand extends AbstractPatternOperand<String, Void> {
    private static final long serialVersionUID = 1L;

    public TestOperand(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public String evaluate(final String value, final Void context) {
        return value;
    }
}