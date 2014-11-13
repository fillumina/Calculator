package com.fillumina.calculator;

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
    private final Solver solver;
    protected final List<Node<T, C>> solutionTree;

    private List<T> solution;

    public SolutionTree(final Solver solver,
            final List<Node<T, C>> solutionTree) {
        this.solver = solver;
        this.solutionTree = solutionTree;
    }

    /** Deep cloning constructor. */
    public SolutionTree(final SolutionTree<T, C> original) {
        this.solver = original.solver;
        this.solutionTree = new LinkedList<>();
        for (final Node<T, C> node : original.solutionTree) {
            solutionTree.add(node.clone());
        }
        this.solution = original.solution; //doesn't make much sense but uh?
    }

    /** @return {@code true} if the solution was found during the last
     *          call to {@link #solve()} or {@link #solve(C)} or
     *          {@link #solve(Solver,C)}.
     * @return
     */
    public boolean isSolved() {
        return solution != null;
    }

    /**
     * @return the first solution found or an {@link SyntaxException} if
     *          something went wrong during parsing or solving the expression.
     */
    public T getSingleSolution() {
        return getSolution().get(0);
    }

    /** @return the last solution found. */
    public List<T> getSolution() {
        return solution;
    }

    /**
     * Solve the solution tree. Use in case there isn't any context.
     * @see #solve(java.lang.Object)
     *
     * @return the solution found or {@code null}.
     * @throws SyntaxErrorException
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
     * @throws SyntaxErrorException
     */
    public List<T> solve(final C context) {
        solve(solver, context);
        return this.solution;
    }

    /**
     * Try to solve the given solution tree using the passed {@link Solver}.
     * If the solution cannot be found
     * (there could be less variable defined than required) {@code null} is
     * returned.
     *
     * @param context the context (define the missing variables here)
     * @return the solution if found otherwise {@code null}.
     * @throws SyntaxErrorException
     */
    protected void solve(final Solver solver, final C context) {
        this.solution = solver.solve(solutionTree, context);
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
