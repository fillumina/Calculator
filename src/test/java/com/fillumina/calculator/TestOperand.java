package com.fillumina.calculator;

import com.fillumina.calculator.element.AbstractPatternElement;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestOperand extends AbstractPatternElement<String, Void> {
    private static final long serialVersionUID = 1L;
    
    public TestOperand(String symbolRegexp, int priority) {
        super(priority, symbolRegexp);
    }

    @Override
    public String evaluate(String value, List<String> params, Void context) {
        return value;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
