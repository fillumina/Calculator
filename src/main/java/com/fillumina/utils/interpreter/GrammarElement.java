package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * The basic element that compose a grammar.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface GrammarElement<T,C> extends Comparable<GrammarElement<T, C>> {

    /** Search for the element in the string expression and returns a matcher. */
    GrammarElementMatcher match(final String expression);

    /** Evaluate the element given the parameters and context. **/
    T evaluate(final String value, final List<T> params, final C context);

    /**
     * Defines the required parameter after the operand.
     * It means only the maximum number allowed, the
     * passed ones are actually only those available up to that value.
     */
    int getRequiredOperandsAfter();

    /**
     * Defines the required parameter before the operand.
     * It means only the maximum number allowed, the
     * passed ones are actually only those available up to that value.
     */
    int getRequiredOperandsBefore();

    /** Returns the type of the element. See {@link GrammarElementType}. */
    GrammarElementType getType();
}
