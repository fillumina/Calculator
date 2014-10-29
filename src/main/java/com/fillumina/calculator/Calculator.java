package com.fillumina.calculator;

import java.io.Serializable;
import java.util.List;

/**
 * This calculator solves an expression and returns a solution tree
 * encapsulated into a {@link SolutionTree}
 * that can be used to find the solution by specifying the needed
 * variables. It is better than simply using a {@link Calculator} because
 * the initial solution tree has been already simplified so eventually
 * less calculations are going to be performed.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Calculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;
    private final Solver solver;

    public static <T,C> Calculator<T,C> createFast(
            final Iterable<GrammarElement<T,C>> grammar) {
        return new Calculator<>(new DefaultInterpreter<>(grammar),
                FastSolver.INSTANCE);
    }

    public static <T,C> Calculator<T,C> createPruning(
            final Iterable<GrammarElement<T,C>> grammar) {
        return new Calculator<>(new DefaultInterpreter<>(grammar),
                PruningSolver.INSTANCE);
    }

    /**
     * Using by default the pruning grammar which is more flexible thought
     * slightly slower.
     */
    public Calculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(new DefaultInterpreter<>(grammar), PruningSolver.INSTANCE);
    }

    public Calculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this(new DefaultInterpreter<>(grammar), solver);
    }

    public Calculator(final Interpreter<T, C> interpreter,
            final Solver solver) {
        this.interpreter = interpreter;
        this.solver = solver;
    }

    /**
     * @return a {@link SolutionTree} that can be used to get the actual solution.
     */
    public SolutionTree<T,C> createSolutionTree(final String expression) {
        final List<Node<T,C>> solutionTree =
                interpreter.buildSolutionTree(expression);
        return new SolutionTree<>(solver, solutionTree);
    }

    /** Returns the first result or {@code null} if no solution can be found. */
    public T solveSingleValue(final String expression) {
        return solve(null, expression).get(0);
    }

    /** Returns the first result or {@code null} if no solution can be found. */
    public T solveSingleValue(final C context, final String expression) {
        return solve(context, expression).get(0);
    }

    /**
     * Returns the entire (eventually multi-rooted or partially solved)
     * solved tree. For most uses it is preferable to use
     * {@link #createSolutionTree(java.lang.String, java.lang.Object) }.
     */
    public List<T> solve(final String expression) {
        final List<Node<T,C>> solutionTree =
                interpreter.buildSolutionTree(expression);
        return solver.solve(solutionTree, null);
    }

    /**
     * Returns the entire (eventually multi-rooted or partially solved)
     * solved tree. For most uses it is preferable to use
     * {@link #createSolutionTree(java.lang.String, java.lang.Object) }.
     */
    public List<T> solve(final C context, final String expression) {
        final List<Node<T,C>> solutionTree =
                interpreter.buildSolutionTree(expression);
        return solver.solve(solutionTree, context);
    }
}
