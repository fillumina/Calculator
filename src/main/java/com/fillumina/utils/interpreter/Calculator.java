package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.GrammarElement;
import java.util.List;

/**
 *
 * @author fra
 */
public class Calculator<T,C> {
    private final Interpreter interpreter;
    private final DefaultSolver<T,C> solver;

    public Calculator(final List<GrammarElement> grammar) {
        this.interpreter = new Interpreter(grammar);
        this.solver = new DefaultSolver<T,C>();
    }

    public Calculator(final List<GrammarElement> grammar,
            final DefaultSolver<T,C> solver) {
        this.interpreter = new Interpreter(grammar);
        this.solver = solver;
    }

    public List<T> solve(final String expression, final C context) {
        final List<Node> solutionTree = interpreter.parse(expression);
        return solver.solve(solutionTree, context);
    }
}
