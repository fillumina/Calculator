package com.fillumina.calculator;

import java.util.List;

/**
 * A {@link SolutionTree} able to do simplifications.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingSolutionTree<T,C> extends SolutionTree<T,C> {

    private final Solver simplifier;

    public SimplifyingSolutionTree(final Solver solver,
            final Solver simplifier,
            final List<Node<T, C>> solutionTree) {
        super(solver, solutionTree);
        this.simplifier = simplifier;
    }

    public SimplifyingSolutionTree(SimplifyingSolutionTree<T, C> original) {
        super(original);
        this.simplifier = original.simplifier;
    }

    /**
     * Use in case there isn't any context.
     *
     * @see #simplify(java.lang.Object)
     *
     * @return the solution found or {@code null}.
     */
    public List<T> simplify() {
        return simplify(null);
    }

    /**
     * Try to solve the given solution tree. If the solution cannot be found
     * (there could be less variable defined than required) {@code null} is
     * returned.
     *
     * @param context the context (define the missing variables here)
     * @return the solution if found otherwise {@code null}.
     */
    public List<T> simplify(final C context) {
        solve(simplifier, context);
        return getSolution();
    }
}
