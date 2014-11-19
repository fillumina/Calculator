package com.fillumina.calculator;

import java.util.List;

/**
 * Solves a solution tree into a list of solutions. The context is passed
 * around to store variables and informations about the calculation.
 *
 * @param T     the type of the result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Solver {

    /**
     * Solves a solution tree into a list of solutions passing around the
     * given context.
     *
     * @param <T>           the type of the result
     * @param <C>           the type of the context
     * @param solutionTree  a {@link List} of {@link Node}s
     * @param context       a context to be passed around to evaluators
     * @return              a list of solutions
     */
    <T,C> List<T> solve(final List<Node<T,C>> solutionTree, final C context);
}
