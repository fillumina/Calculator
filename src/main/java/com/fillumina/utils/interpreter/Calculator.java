package com.fillumina.utils.interpreter;

import java.util.List;

/**
 *
 * @param T the type of the elements (i.e. <code>Double</code>)
 * @param C the type of the context (i.e. <code>Map&lt;String, Double&gt;</code>)
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Calculator<T,C> {
    private final Interpreter<T,C> interpreter;
    private final Solver solver;

    @SuppressWarnings("unchecked")
    public Calculator(final Iterable<GrammarElement<T,C>> grammar) {
        this.interpreter = new Interpreter<>(grammar);
        this.solver = DefaultSolver.INSTANCE;
    }

    public Calculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this.interpreter = new Interpreter<>(grammar);
        this.solver = solver;
    }

    public T solveSingleValue(final String expression, final C context) {
        return solve(expression, context).get(0);
    }

    public List<T> solve(final String expression, final C context) {
        final List<Node<T,C>> solutionTree = interpreter.parse(expression);
        return solver.solve(solutionTree, context);
    }
}
