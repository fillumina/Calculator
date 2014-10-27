package com.fillumina.calculator.grammar.pattern;

import com.fillumina.calculator.grammar.pattern.AbstractOperator;
import java.util.List;
import java.util.Map;

/**
 * It's an operator that doesn't evaluate. It is useful in tests.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestContextOperator
        extends AbstractOperator<String,Map<String,String>> {
    private static final long serialVersionUID = 1L;

    public TestContextOperator(final String symbolRegexp,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(symbolRegexp, priority, requiredOperandsBefore,
                requiredOperandsAfter);
    }

    @Override
    public String evaluate(final String value,
            final List<String> params, final Map<String,String> context) {
        return '{' + value + params.toString() + '}';
    }
}
