package com.fillumina.calculator;

/**
 * Different types of {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public enum GrammarElementType {
    /**
     * Defines the start of an enclosed expression that starts with the
     * subsequent element and must be solved before the current one.
     * This element is never evaluated and serves only as a marker to
     * recognize the start of the enclosed expression.
     */
    OPEN_PAR,
    /**
     * Defines the end of an enclosed expression. It's never evaluated.
     */
    CLOSED_PAR,
    /**
     * Defines an operand.
     */
    OPERAND,
    /**
     * Defines an operator that acts on parameters that can be before or
     * after the position of the operand in the expression. If an operand is
     * followed by a parentheses and expects parameters after than all the
     * values in the parentheses will be passed as parameters (up to the
     * maximum specified amount).
     */
    OPERATOR,
    /**
     * Defines a ignorable character used to separate other elements in the
     * expression. This element is never evaluated.
     */
    WHITE_SPACE,
    /**
     * Unrecognized by the grammar it is used to recognized incognita variables.
     */
    UNRECOGNIZED
}
