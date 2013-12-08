package com.fillumina.utils.interpreter.util;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface TreeVisitor<T> {

    Iterable<T> getChildren(T node);
    void onNode(int level, T node);
}
