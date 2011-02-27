package com.fillumina.utils.interpreter;

import java.util.List;

/**
 *
 * @author fra
 */
public interface Solver<T, C> {

    List<T> solve(final List<Node> nodeTree, final C context);

}
