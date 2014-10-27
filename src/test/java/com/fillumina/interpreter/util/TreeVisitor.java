package com.fillumina.interpreter.util;

/**
 * Tree's node visitor. It provides also a way to get the node's children.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface TreeVisitor<T> {

    Iterable<T> getChildren(T node);
    void onNode(int level, T node);
}
