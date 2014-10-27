package com.fillumina.calculator;

import java.util.List;

/**
 * Solves a solution tree into a list of solutions. The context is passed
 * around to store variables and informations about the calculation.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Solver {

    <T,C> List<T> solve(final List<Node<T,C>> solutionTree, final C context);
}
