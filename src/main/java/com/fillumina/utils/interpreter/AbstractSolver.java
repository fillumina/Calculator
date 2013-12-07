package com.fillumina.utils.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractSolver implements Solver {

    @Override
    public <T, C> List<T> solve(final List<Node<T,C>> nodeTree,
            final C context) {
        final List<T> params = new ArrayList<>();
        for (Node<T,C> node : nodeTree) {
            final List<T> parameters = solve(node.getChildren(), context);
            final T evaluated = evaluate(node, parameters, context);
            params.add(evaluated);
        }
        return params;
    }

    @SuppressWarnings(value = "unchecked")
    public abstract <T, C> T evaluate(final Node<T,C> node,
            final List<T> params,
            final C context);

}
