package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author fra
 */
public interface GrammarElement<T,C> extends Comparable<GrammarElement<T, C>> {

    public interface MatchIndex {
        boolean found();
        int start();
        int end();
    }

    MatchIndex match(String expression);

    T evaluate(final String value, final List<T> params, final C context);

    int getRequiredOperandsAfter();
    int getRequiredOperandsBefore();

    public static enum Type {
        OPEN_PAR, CLOSED_PAR, OPERAND, OPERATOR, WHITE_SPACE, UNRECOGNIZED}

    boolean isType(Type type);
}
