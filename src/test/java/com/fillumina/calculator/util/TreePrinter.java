package com.fillumina.calculator.util;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.Node;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Helper to give a string representation of a solution tree.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreePrinter implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final NodePrinter FULL_NODE = new NodePrinter() {
        private static final long serialVersionUID = 1L;

        @Override
        public <T,C> void printNode(final StringBuilder builder,
                final int level,
                final Node<T,C> node) {
            builder.append(spaces(level))
                    .append(node.getExpression())
                    .append(" ");
            final GrammarElement<T, C> grammarElement = node.getGrammarElement();
            if (grammarElement != null) {
                builder.append(grammarElement.getType().toString());
            }
            if (node.hasValue()) {
                builder.append(" -> ").append(node.getValue());
            }
            builder.append("\n");
        }
    };

    public static final NodePrinter SHORT_NODE = new NodePrinter() {
        private static final long serialVersionUID = 1L;

        @Override
        public <T,C> void printNode(final StringBuilder builder,
                final int level,
                final Node<T,C> node) {
            builder.append(spaces(level))
                    .append(node.getExpression())
                    .append("\n");
        }
    };

    public static final NodePrinter NODE_TOSTRING = new NodePrinter() {
        private static final long serialVersionUID = 1L;

        @Override
        public <T,C> void printNode(final StringBuilder builder,
                final int level,
                final Node<T,C> node) {
            builder.append(node).append("\n");
        }
    };

    private TreePrinter() {}

    private static class PrintTreeVisitor<T,C>
            implements TreeVisitor<Node<T,C>> {
        private static final long serialVersionUID = 1L;

        final NodePrinter nodePrinter;
        final StringBuilder builder = new StringBuilder();

        public PrintTreeVisitor(final NodePrinter nodePrinter) {
            this.nodePrinter = nodePrinter;
        }

        @Override
        public Iterable<Node<T,C>> getChildren(final Node<T,C> node) {
            return node.getChildren();
        }

        @Override
        public void onNode(final int level, final Node<T,C> node) {
            nodePrinter.printNode(builder, level, node);
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }

    public static <T,C> String prettyPrintFull(
            final Iterable<Node<T,C>> solutionTree) {
        return prettyPrintList(solutionTree, FULL_NODE);
    }

    public static <T,C> String prettyPrintShort(
            final Iterable<Node<T,C>> solutionTree) {
        return prettyPrintList(solutionTree, SHORT_NODE);
    }

    public static <T, C> String prettyPrintList(
            final Iterable<Node<T, C>> solutionTree,
            final NodePrinter nodePrinter) {
        final StringBuilder builder = new StringBuilder();
        for (Node<T,C> node : solutionTree) {
            builder.append(prettyPrint(node, nodePrinter));
        }
        return builder.toString();
    }

    public static <T,C> String prettyPrint(final Node<T,C> tree,
            final NodePrinter nodePrinter) {
        final PrintTreeVisitor<T, C> printTreeVisitor =
                new PrintTreeVisitor<>(nodePrinter);
        final TreeTraverser<Node<T,C>> treeTraverser =
                new TreeTraverser<>(printTreeVisitor);
        treeTraverser.traverse(tree);
        return printTreeVisitor.toString();
    }

    public static String spaces(final int quantity) {
        final char[] c = new char[quantity];
        Arrays.fill(c, ' ');
        return String.valueOf(c);
    }
}
