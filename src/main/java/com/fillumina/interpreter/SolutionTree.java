package com.fillumina.interpreter;

import com.fillumina.interpreter.grammar.UndefinedVariablesFinder;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulate a solution tree and try to solve it by specifying
 * the required variables in the context. It can be deep cloned.
 * It's not thread safe, clone it if you need to use it in different threads.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionTree<T, C> implements Cloneable {
    private final List<Node<T, C>> solutionTree;
    private final Solver solver;

    private List<T> solution;

    public SolutionTree(final Solver solver,
            final List<Node<T, C>> solutionTree) {
        this.solver = solver;
        this.solutionTree = solutionTree;
    }

    public SolutionTree(final SolutionTree<T, C> original) {
        this.solver = original.solver;
        this.solutionTree = new LinkedList<>();
        for (final Node<T, C> node : original.solutionTree) {
            solutionTree.add(node.clone());
        }
        this.solution = original.solution; //doesn't make much sense but uh?
    }

    public boolean isSolved() {
        return solution != null;
    }

    public T getSingleValueSolution() {
        if (isSolved()) {
            return getSolution().get(0);
        }
        return null;
    }

    public List<T> getSolution() {
        return solution;
    }

    /**
     * Use in case there isn't any context.
     * @see #solve(java.lang.Object)
     *
     * @return the solution found or {@code null}.
     */
    public List<T> solve() {
        return solve(null);
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
        this.solution = solver.solve(solutionTree, context);
        return this.solution;
    }

    /** @return a list of the undefined variables found in the solution tree. */
    public List<String> getUndefinedVariables() {
        return UndefinedVariablesFinder.INSTANCE.find(solutionTree);
    }

    @Override
    protected SolutionTree<T, C> clone() {
        return new SolutionTree<>(this);
    }
}
