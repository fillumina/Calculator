package com.fillumina.utils.interpreter;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author fra
 */
public class TreePrinter<T,C> {
    private final Node<T,C> tree;

    public static <T,C> String prettyPrint(
            final Collection<Node<T,C>> collection) {
        final StringBuilder builder = new StringBuilder();
        for (Node<T,C> node : collection) {
            builder.append(prettyPrint(node));
        }
        return builder.toString();
    }

    public static <T,C> String prettyPrint(final Node<T,C> node) {
        return new TreePrinter<T,C>(node).toString();
    }

    private TreePrinter(final Node<T,C> node) {
        this.tree = node;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        addSubNodes(builder, 0, tree);
        return builder.toString();
    }

    private void addSubNodes(final StringBuilder builder,
            final int level,
            final Node<T,C> node) {
        builder.append(spaces(level)).append(node.getValue()).append("\n");
        for (Node<T,C> n: node.getChildren()) {
            addSubNodes(builder, level + 1, n);
        }
    }

    private String spaces(final int quantity) {
        final char[] c = new char[quantity];
        Arrays.fill(c, ' ');
        return String.valueOf(c);
    }
}
