package com.fillumina.utils.interpreter.util;

import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreeTraverser<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final TreeVisitor<T> treeTraverser;

    public TreeTraverser(final TreeVisitor<T> treeTraverser) {
        this.treeTraverser = treeTraverser;
    }

    public void traverse(final T rootNode) {
        traverse(0, rootNode);
    }

    private void traverse(final int level, final T node) {
        treeTraverser.onNode(level, node);
        for (T t: treeTraverser.getChildren(node)) {
            traverse(level + 1, t);
        }
    }
}
