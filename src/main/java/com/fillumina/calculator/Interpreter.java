package com.fillumina.calculator;

import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Interpreter<T, C> {

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    List<Node<T, C>> buildSolutionTree(final String expression);
}
