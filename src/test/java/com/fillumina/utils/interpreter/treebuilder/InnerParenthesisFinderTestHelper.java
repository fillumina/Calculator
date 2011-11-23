package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractEvaluableGrammarElement;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class InnerParenthesisFinderTestHelper {

    public static class EvaluableNode extends Node {
        private static final long serialVersionUID = 1L;

        public EvaluableNode(final String value) {
            super(value, new AbstractEvaluableGrammarElement<Long, Void>("\\" +value, 1) {

                private static final long serialVersionUID = 1L;

                @Override
                public Long evaluate(final String value,
                        final List<Long> params,
                        final Void context) {
                    return Long.parseLong(value);
                }
            });
        }
    }

    public static class OpenParNode extends Node {
        private static final long serialVersionUID = 1L;

        public OpenParNode() {
            super("(", new OpenParenthesis("\\("));
        }
    }

    public static class CloseParNode extends Node {
        private static final long serialVersionUID = 1L;

        public CloseParNode() {
            super(")", new CloseParenthesis("\\)"));
        }
    }


    public List<Node> buildNodeList(final String expression) {
        final List<Node> list = new ArrayList<Node>();
        for (char c: expression.toCharArray()) {
            switch(c) {
                case '(': list.add(new OpenParNode()); break;
                case ')': list.add(new CloseParNode()); break;
                default: list.add(new EvaluableNode(String.valueOf(c)));
            }
        }
        return list;
    }

    public String buildExpression(final List<Node> nodeList) {
        final StringBuilder builder = new StringBuilder();
        for (Node node: nodeList) {
            if (node.isOfType(OpenParenthesis.class)) {
                builder.append('(');
            } else if (node.isOfType(CloseParenthesis.class)) {
                builder.append(')');
            } else {
                builder.append(node.getValue());
            }
        }
        return builder.toString();
    }

    @Test
    public void shouldBuildTheRightNodeListFromExpression() {
        final String expression = "1+12-(7-3)";
        final List<Node> list = buildNodeList(expression);
        assertEquals(expression, buildExpression(list));
    }
}
