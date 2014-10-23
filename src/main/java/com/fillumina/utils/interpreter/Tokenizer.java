package com.fillumina.utils.interpreter;

import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Tokenizer<T, C> {

    List<Node<T, C>> tokenize(final String expression);
}
