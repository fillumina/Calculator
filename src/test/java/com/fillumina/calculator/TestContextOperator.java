package com.fillumina.calculator;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestContextOperator extends TestContextOperand {
    private static final long serialVersionUID = 1L;
    private final int before, after;

    public TestContextOperator(String symbolRegexp, int priority, int before, int after) {
        super(symbolRegexp, priority);
        this.before = before;
        this.after = after;
    }

    @Override
    public String evaluate(String value, List<String> params,
            Map<String, String> context) {
        return "@" + params.toString();
    }

    @Override
    public int getRequiredOperandsBefore() {
        return before;
    }

    @Override
    public int getRequiredOperandsAfter() {
        return after;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }
}
