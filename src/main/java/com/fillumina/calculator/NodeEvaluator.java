package com.fillumina.calculator;

import java.io.Serializable;
import java.util.List;

/**
 * Evaluates a node.
 *
 * @param T the type of the result
 * @param C the type of the context
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface NodeEvaluator extends Serializable {

    <T, C> T evaluate(final Node<T, C> node, final List<T> params,
            final C context);
}
