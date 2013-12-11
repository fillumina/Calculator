package com.fillumina.utils.interpreter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OptimizedSolutionTree<T, C> implements Cloneable {
    private final List<Node<T, C>> solutionTree;
    private List<T> solution;

    public OptimizedSolutionTree(final List<Node<T, C>> solutionTree,
            final List<T> solution) {
        this.solutionTree = solutionTree;
        this.solution = solution;
    }

    public OptimizedSolutionTree(final OptimizedSolutionTree<T, C> original) {
        final List<Node<T, C>> originalSolutionTree = original.solutionTree;
        this.solutionTree = new LinkedList<>();
        for (final Node<T, C> node : originalSolutionTree) {
            solutionTree.add(node.clone());
        }
        this.solution = original.solution; //doesn't make much sense but uh?
    }

    public boolean hasStaticSolution() {
        return solution != null;
    }

    public T getSingleValueSolution() {
        return getSolution().get(0);
    }

    public List<T> getSolution() {
        return solution;
    }

    /**
     *
     * @param context the context
     * @return the solution if found otherwise {@code null}.
     */
    public List<T> solve(final C context) {
        return this.solution = PruningSolver.INSTANCE.solve(solutionTree,
                context);
    }

    public Collection<String> getUndefinedVariables() {
        return UndefinedVariablesFinder.INSTANCE.find(solutionTree);
    }

    @Override
    protected OptimizedSolutionTree<T, C> clone() {
        return new OptimizedSolutionTree<>(this);
    }
}
