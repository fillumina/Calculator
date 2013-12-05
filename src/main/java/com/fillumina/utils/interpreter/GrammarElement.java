package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface GrammarElement<T,C> extends Comparable<GrammarElement<T, C>> {

    //TODO export GrammarElementMatchIndex
    interface GrammarElementMatchIndex {
        boolean found();
        int start();
        int end();
    }

    GrammarElementMatchIndex match(String expression);

    T evaluate(final String value, final List<T> params, final C context);

    int getRequiredOperandsAfter();
    int getRequiredOperandsBefore();

    //TODO export Type
    public static enum Type {
        OPEN_PAR, CLOSED_PAR, OPERAND, OPERATOR, WHITE_SPACE, UNRECOGNIZED}

    boolean isType(Type type);
}
