package com.fillumina.utils.interpreter.util;

import com.fillumina.utils.interpreter.Node;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Helper to give a string representation of a solution tree.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreePrinter<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static class PrintTreeVisitor<T,C>
            implements TreeVisitor<Node<T,C>> {
        private static final long serialVersionUID = 1L;

        final StringBuilder builder = new StringBuilder();

        @Override
        public Iterable<Node<T,C>> getChildren(final Node<T,C> node) {
            return node.getChildren();
        }

        @Override
        public void onNode(final int level, final Node<T,C> node) {
            builder.append(spaces(level))
                    .append(node.getExpression())
                    .append(" ")
                    .append(node.getGrammarElement().getType().toString());
            if (node.hasValue()) {
                builder.append(" -> ").append(node.getValue());
            }
            builder.append("\n");
        }

        private String spaces(final int quantity) {
            final char[] c = new char[quantity];
            Arrays.fill(c, ' ');
            return String.valueOf(c);
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }

    public static <T,C> String prettyPrint(
            final Iterable<Node<T,C>> solutionTree) {
        final StringBuilder builder = new StringBuilder();
        for (Node<T,C> node : solutionTree) {
            builder.append(prettyPrint(node));
        }
        return builder.toString();
    }

    public static <T,C> String prettyPrint(final Node<T,C> tree) {
        final PrintTreeVisitor<T, C> printTreeVisitor =
                new PrintTreeVisitor<>();
        final TreeTraverser<Node<T,C>> treeTraverser =
                new TreeTraverser<>(printTreeVisitor);
        treeTraverser.traverse(tree);
        return printTreeVisitor.toString();
    }
}
