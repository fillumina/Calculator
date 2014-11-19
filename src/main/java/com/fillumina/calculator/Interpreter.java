package com.fillumina.calculator;

import java.util.List;

/**
 * Parses an expression into a solution tree which is a {@link List} of
 * {@link Node}s that contains {@link GrammarElement}s.
 *
 * @param T the type of the result
 * @param C the type of the context
 *
 * @see Node
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Interpreter<T, C> {

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    List<Node<T, C>> buildSolutionTree(final String expression);
}
