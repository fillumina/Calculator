package com.fillumina.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link Solver} that doesn't modify the solution tree.
 *
 * @see SimplifyingSolver
 *
 * @param T the type of the result
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;

    public static final Solver INSTANCE = new DefaultSolver();

    private final NodeEvaluator evaluator;

    public DefaultSolver() {
        this(new DefaultNodeEvaluator());
    }

    public DefaultSolver(final NodeEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public <T,C> List<T> solve(final List<Node<T,C>> solutionTree,
            final C context) {
        final List<T> results = new ArrayList<>(solutionTree.size());
        for (final Node<T,C> node : solutionTree) {
            final List<Node<T, C>> children = node.getChildren();
            final List<T> parameters = solve(children, context);
            final T evaluated = evaluator.evaluate(node, parameters, context);
            results.add(evaluated);
        }
        return results;
    }
}
