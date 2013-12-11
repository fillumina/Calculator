package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * Solves a solution tree.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Solver {

    <T,C> List<T> solve(final List<Node<T,C>> nodeTree, final C context);
}
