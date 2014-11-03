package com.fillumina.calculator.grammar.treebuilder;

import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.grammar.pattern.AbstractOperand;
import com.fillumina.calculator.grammar.pattern.CloseParentheses;
import com.fillumina.calculator.grammar.pattern.OpenParentheses;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class InnerParenthesisFinderTestHelper {

    public static class EvaluableNode extends Node<Long, Void> {
        private static final long serialVersionUID = 1L;

        public EvaluableNode(final String value) {
            super(value, new AbstractOperand<Long, Void>("\\" +value, 1) {

                private static final long serialVersionUID = 1L;

                @Override
                public Long evaluate(final String value,
                        final Void context) {
                    return Long.parseLong(value);
                }
            });
        }
    }

    public static class OpenParNode extends Node<Long, Void> {
        private static final long serialVersionUID = 1L;

        public OpenParNode() {
            super("(", new OpenParentheses<Long, Void>("\\("));
        }
    }

    public static class CloseParNode extends Node<Long, Void> {
        private static final long serialVersionUID = 1L;

        public CloseParNode() {
            super(")", new CloseParentheses<Long, Void>("\\)"));
        }
    }


    public List<Node<Long, Void>> buildNodeList(final String expression) {
        final List<Node<Long, Void>> list = new ArrayList<>();
        for (char c: expression.toCharArray()) {
            switch(c) {
                case '(': list.add(new OpenParNode()); break;
                case ')': list.add(new CloseParNode()); break;
                default: list.add(new EvaluableNode(String.valueOf(c)));
            }
        }
        return list;
    }

    public String buildExpression(final List<Node<Long, Void>> nodeList) {
        final StringBuilder builder = new StringBuilder();
        for (Node<Long, Void> node: nodeList) {
            if (node.isOfType(GrammarElementType.OPEN_PAR)) {
                builder.append('(');
            } else if (node.isOfType(GrammarElementType.CLOSED_PAR)) {
                builder.append(')');
            } else {
                builder.append(node.getExpression());
            }
        }
        return builder.toString();
    }

    @Test
    public void shouldBuildTheRightNodeListFromExpression() {
        final String expression = "1+12-(7-3)";
        final List<Node<Long, Void>> list = buildNodeList(expression);
        assertEquals(expression, buildExpression(list));
    }
}
