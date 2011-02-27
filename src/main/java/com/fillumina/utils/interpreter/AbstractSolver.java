package com.fillumina.utils.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fra
 */
public abstract class AbstractSolver<T,C> implements Solver<T, C> {

    @Override
    public List<T> solve(final List<Node> nodeTree, final C context) {
        final List<T> params = new ArrayList<T>();
        for (Node node : nodeTree) {
            params.add(evaluate(node, solve(node.getNodes(), context), context));
        }
        return params;
    }

    @SuppressWarnings(value = "unchecked")
    public abstract T evaluate(final Node node, final List<T> params,
            final C context);

}
