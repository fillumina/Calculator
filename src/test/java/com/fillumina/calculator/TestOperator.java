package com.fillumina.calculator;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestOperator extends TestOperand {
    private static final long serialVersionUID = 1L;
    private final int before, after;

    public TestOperator(String symbolRegexp, int priority, int before, int after) {
        super(symbolRegexp, priority);
        this.before = before;
        this.after = after;
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
