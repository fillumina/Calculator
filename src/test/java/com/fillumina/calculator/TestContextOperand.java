package com.fillumina.calculator;

import com.fillumina.calculator.element.AbstractPatternElement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestContextOperand
        extends AbstractPatternElement<String, Map<String,String>> {
    private static final long serialVersionUID = 1L;

    public TestContextOperand(String symbolRegexp, int priority) {
        super(priority, symbolRegexp);
    }

    @Override
    public String evaluate(String value, List<String> params,
            Map<String,String> context) {
        return value;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
