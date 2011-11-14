package com.fillumina.utils.interpreter;

import java.util.Arrays;

/**
 *
 * @author fra
 */
public class TreePrinter {
    private final Node tree;

    public static String prettyPrint(final Node node) {
        return new TreePrinter(node).toString();
    }

    public TreePrinter(final Node node) {
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
            final Node node) {
        builder.append(spaces(level)).append(node.getValue()).append("\n");
        for (Node n: node.getChildren()) {
            addSubNodes(builder, level + 1, n);
        }
    }

    private String spaces(final int quantity) {
        final char[] c = new char[quantity];
        Arrays.fill(c, ' ');
        return String.valueOf(c);
    }
}
