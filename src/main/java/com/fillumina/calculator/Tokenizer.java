package com.fillumina.calculator;

import java.util.List;

/**
 * Reads the grammar elements in the given expression and returns a list
 * of nodes representing them.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Tokenizer<T, C> {

    /** Parses a string expression into a {@link List} of {@link Node}s. */
    List<Node<T, C>> tokenize(final String expression);
}
