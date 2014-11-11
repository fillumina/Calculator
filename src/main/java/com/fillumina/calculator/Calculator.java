package com.fillumina.calculator;

import com.fillumina.calculator.interpreter.DefaultInterpreter;
import java.io.Serializable;
import java.util.List;

/**
 * Solves an expression and returns a solution tree.
 *
 * @param T the type of the result
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Calculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;
    private final Solver solver;

    /** Specify the grammar to use, default {@link Interpreter} is used. */
    public Calculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(new DefaultInterpreter<>(grammar), DefaultSolver.INSTANCE);
    }

    /**
     * Specify the grammar and the {@link Solver} to use, default
     * {@link Interpreter} is assumed.
     */
    public Calculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this(new DefaultInterpreter<>(grammar), solver);
    }

    /** Specify the {@link Interpreter} and the {@link Solver}. */
    public Calculator(final Interpreter<T, C> interpreter,
            final Solver solver) {
        this.interpreter = interpreter;
        this.solver = solver;
    }

    protected Solver getSolver() {
        return solver;
    }

    /**
     * @return a {@link SolutionTree} that can be used to get the actual
     *         solution.
     */
    public SolutionTree<T,C> createSolutionTree(final String expression) {
        final List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return new SolutionTree<>(solver, solutionTree);
    }

    /** Returns the first result or {@code null} if no solution can be found. */
    public T solveSingleValue(final String expression) {
        final List<T> solutionList = solve(null, expression);
        if (solutionList == null) {
            return null;
        }
        return solutionList.get(0);
    }

    /** Returns the first result or {@code null} if no solution can be found. */
    public T solveSingleValue(final C context, final String expression) {
        final List<T> solutionList = solve(context, expression);
        if (solutionList == null) {
            return null;
        }
        return solutionList.get(0);
    }

    /**
     * Returns the entire (eventually multi-rooted or partially solved)
     * solved tree. For most uses it is preferable to use
     * {@link #createSolutionTree(java.lang.String, java.lang.Object) }.
     */
    public List<T> solve(final String expression) {
        List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return solver.solve(solutionTree, null);
    }

    /**
     * Returns the entire (eventually multi-rooted or partially solved)
     * solution tree as a list.
     */
    public List<T> solve(final C context, final String expression) {
        List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return solver.solve(solutionTree, context);
    }

    protected List<Node<T, C>> createSolutionTreeList(final String expression) {
        return interpreter.buildSolutionTree(expression);
    }
}
