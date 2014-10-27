package com.fillumina.interpreter;

import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Calculator<T, C> {

    /** Returns the entire (eventually multi-rooted) solved tree. */
    List<T> solve(final String expression, final C context);

    /** Returns the first result. */
    T solveSingleValue(final String expression, final C context);
}
