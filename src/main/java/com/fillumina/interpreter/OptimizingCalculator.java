package com.fillumina.interpreter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * This calculator solves an expression and returns a solution tree
 * encapsulated into a {@link SolutionOptimizer}
 * that can be used to find the solution by specifying the needed
 * variables. It is better than simply using a {@link Calculator} because
 * the initial solution tree has been already simplified so eventually
 * less calculations are going to be performed.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OptimizingCalculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;

    public OptimizingCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this.interpreter = new DefaultInterpreter<>(grammar);
    }

    @SuppressWarnings("unchecked")
    public SolutionOptimizer<T,C> createSolutionTree(
            final String expression) {
        return createSolutionTree(expression, (C) Collections.EMPTY_MAP);
    }

    /**
     * The variables passed in {@code context} will be evaluated and the
     * result returned as a {@link SolutionOptimizer}.
     */
    public SolutionOptimizer<T,C> createSolutionTree(
            final String expression, final C context) {
        final List<Node<T,C>> solutionTree =
                interpreter.buildSolutionTree(expression);
        final List<T> solution =
                PruningSolver.INSTANCE.solve(solutionTree, context);
        return new SolutionOptimizer<>(solutionTree, solution);
    }
}
