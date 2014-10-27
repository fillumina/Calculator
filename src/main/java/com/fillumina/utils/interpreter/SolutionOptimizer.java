package com.fillumina.utils.interpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulate a solution tree and try to solve it by specifying
 * the required constants in the context.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionOptimizer<T, C> implements Cloneable {
    private final List<Node<T, C>> solutionTree;
    private List<T> solution;

    public SolutionOptimizer(final List<Node<T, C>> solutionTree,
            final List<T> solution) {
        this.solutionTree = solutionTree;
        this.solution = solution;
    }

    public SolutionOptimizer(final SolutionOptimizer<T, C> original) {
        final List<Node<T, C>> originalSolutionTree = original.solutionTree;
        this.solutionTree = new LinkedList<>();
        for (final Node<T, C> node : originalSolutionTree) {
            solutionTree.add(node.clone());
        }
        this.solution = original.solution; //doesn't make much sense but uh?
    }

    public boolean isSolved() {
        return solution != null;
    }

    public T getSingleValueSolution() {
        return getSolution().get(0);
    }

    public List<T> getSolution() {
        return solution;
    }

    /**
     * Try to solve the given solution tree. If the solution cannot be found
     * (there could be less variable defined than required) {@code null} is
     * returned.
     *
     * @param context the context (define the missing variables here)
     * @return the solution if found otherwise {@code null}.
     */
    public List<T> solve(final C context) {
        this.solution = PruningSolver.INSTANCE.solve(solutionTree, context);
        return this.solution;
    }

    /** @return a list of the undefined variables found in the solution tree. */
    public List<String> getUndefinedVariables() {
        return UndefinedVariablesFinder.INSTANCE.find(solutionTree);
    }

    @Override
    protected SolutionOptimizer<T, C> clone() {
        return new SolutionOptimizer<>(this);
    }
}
