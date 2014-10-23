package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * Parses a string expression and returns a multi-rooted solution tree.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Interpreter<T, C> {

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    List<Node<T, C>> parse(final String expression);
}
