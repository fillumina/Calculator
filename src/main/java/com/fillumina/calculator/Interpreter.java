package com.fillumina.calculator;

import java.util.List;

/**
 *
 * @param T the type of the result
 * @param C the type of the context
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Interpreter<T, C> {

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    List<Node<T, C>> buildSolutionTree(final String expression);
}
