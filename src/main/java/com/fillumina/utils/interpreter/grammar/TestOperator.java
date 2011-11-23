package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.Node;
import java.util.List;

/**
 * It's an operator that doesn't evaluate. It is useful in tests.
 * 
 * @author fra
 */
public class TestOperator extends AbstractOperator<Node,Void> {
    private static final long serialVersionUID = 1L;

    public TestOperator(final String symbolRegexp,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(symbolRegexp, priority, requiredOperandsBefore, requiredOperandsAfter);
    }

    @Override
    public Node evaluate(final Node node, final List<Node> params,
            final Void context) {
        return node;
    }

}
