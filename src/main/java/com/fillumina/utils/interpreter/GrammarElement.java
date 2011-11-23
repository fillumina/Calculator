package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author fra
 */
public interface GrammarElement<T,C> extends Comparable<GrammarElement<T, C>> {

    public interface MatchedIndexes {
        boolean found();
        int start();
        int end();
    }

    MatchedIndexes match(String expression);

    T evaluate(final String value, final List<T> params, final C context);
}
