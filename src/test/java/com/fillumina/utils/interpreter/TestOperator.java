package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.grammar.AbstractOperator;
import java.util.List;

/**
 * It's an operator that doesn't evaluateuate. It is useful in tests.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TestOperator extends AbstractOperator<String,Void> {
    private static final long serialVersionUID = 1L;

    public TestOperator(final String symbolRegexp,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(symbolRegexp, priority, requiredOperandsBefore, requiredOperandsAfter);
    }

    @Override
    public String evaluate(final String value,
            final List<String> params, final Void context) {
        return value;
    }

    @Override
    public boolean isType(final GrammarElementType type) {
        return GrammarElementType.OPERATOR.equals(type);
    }

}
