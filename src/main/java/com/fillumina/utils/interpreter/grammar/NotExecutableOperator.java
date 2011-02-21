package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.Node;
import java.util.List;

/**
 *
 * @author fra
 */
public class NotExecutableOperator extends Operator<Node,Void> {
    private static final long serialVersionUID = 1L;

    public NotExecutableOperator(final String symbolRegexp,
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
