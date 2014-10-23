package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * This calculator solve an expression and returns a solution tree
 * encapsulated into a {@link SolutionOptimizer}
 * that can be used to find the solution by specifying the needed
 * variables.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OptimizerCalculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;

    public OptimizerCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this.interpreter = new GrammarBasedInterpreter<>(grammar);
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
        final List<Node<T,C>> solutionTree = interpreter.parse(expression);
        final List<T> solution =
                PruningSolver.INSTANCE.solve(solutionTree, context);
        return new SolutionOptimizer<>(solutionTree, solution);
    }
}
