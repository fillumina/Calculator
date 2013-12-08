package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link Solver} that doesn't modify the solution tree.
 *
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final Solver INSTANCE =
            new DefaultSolver(new DefaultEvaluator());

    private final Evaluator evaluator;

    public DefaultSolver(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public <T, C> List<T> solve(final List<Node<T,C>> nodeTree,
            final C context) {
        final List<T> params = new ArrayList<>();
        for (Node<T,C> node : nodeTree) {
            final List<T> parameters = solve(node.getChildren(), context);
            final T evaluated = evaluator.evaluate(node, parameters, context);
            params.add(evaluated);
        }
        return params;
    }
}
