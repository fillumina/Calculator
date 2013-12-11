package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.List;

/**
 * Evaluates a node.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Evaluator extends Serializable {

    <T, C> T evaluate(final Node<T, C> node, final List<T> params,
            final C context);
}