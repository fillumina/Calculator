package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link Solver} that doesn't modify the solution tree.
 * This means that it doesn't optimize it in any way and every time it's
 * called it executes every calculation in the solution tree again.
 * If you need to perform optimizations use {@link PruningSolver}.
 *
 * @see PruningSolver
 *
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;

    public static final Solver INSTANCE = new DefaultSolver();

    private final Evaluator evaluator;

    public DefaultSolver() {
        this(new DefaultEvaluator());
    }

    public DefaultSolver(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public <T, C> List<T> solve(final List<Node<T,C>> nodeTree,
            final C context) {
        final List<T> results = new ArrayList<>(nodeTree.size());
        for (final Node<T,C> node : nodeTree) {
            final List<Node<T, C>> children = node.getChildren();
            final List<T> parameters = solve(children, context);
            final T evaluated = evaluator.evaluate(node, parameters, context);
            results.add(evaluated);
        }
        return results;
    }
}
