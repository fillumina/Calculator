package com.fillumina.utils.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fra
 */
public abstract class AbstractSolver<T,C> implements Solver<T, C> {

    @Override
    public List<T> solve(final List<Node<T,C>> nodeTree, final C context) {
        final List<T> params = new ArrayList<>();
        for (Node<T,C> node : nodeTree) {
            params.add(evaluate(node, solve(node.getChildren(), context), context));
        }
        return params;
    }

    @SuppressWarnings(value = "unchecked")
    public abstract T evaluate(final Node<T,C> node, final List<T> params,
            final C context);

}
