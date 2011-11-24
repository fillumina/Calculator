package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.GrammarElement.Type;
import com.fillumina.utils.interpreter.grammar.AbstractOperator;
import java.util.List;

/**
 * It's an operator that doesn't evaluate. It is useful in tests.
 *
 * @author fra
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
    public String eval(final String value,
            final List<String> params, final Void context) {
        return value;
    }

    @Override
    public boolean isType(final Type type) {
        return Type.OPERATOR.equals(type);
    }

}
