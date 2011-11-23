package com.fillumina.utils.interpreter;

import java.util.List;

/**
 *
 * @param T the type of the elements (i.e. <code>Double</code>)
 * @param C the type of the context (i.e. <code>Map&lt;String, Double&gt;</code>)
 *
 * @author fra
 */
public class Calculator<T,C> {
    private final Interpreter interpreter;
    private final Solver<T,C> solver;

    public Calculator(final List<GrammarElement<T,C>> grammar) {
        this.interpreter = new Interpreter<T,C>(grammar);
        this.solver = new DefaultSolver<T,C>();
    }

    public Calculator(final List<GrammarElement<T,C>> grammar,
            final Solver<T,C> solver) {
        this.interpreter = new Interpreter<T,C>(grammar);
        this.solver = solver;
    }

    public List<T> solve(final String expression, final C context) {
        final List<Node> solutionTree = interpreter.parse(expression);
        return solver.solve(solutionTree, context);
    }
}
