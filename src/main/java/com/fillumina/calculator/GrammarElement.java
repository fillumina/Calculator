package com.fillumina.calculator;

import java.util.List;

/**
 * The basic element that compose a grammar.
 * It implements {@link Comparable} to allows higher priority elements to be
 * evaluated first. A grammar element is stateless and works in two different
 * steps:
 * <ol>
 * <li>Reads a string expression and extracts a substring that it can parse
 *      meaningfully
 *      ({@link #match(com.fillumina.calculator.GrammarElement,String)});
 * <li>Parse the recognized substring and returns an interpreted value
 *      ({@link #evaluate(String,List,Object)}).
 * </ol>
 *
 * @param T     the type of the result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface GrammarElement<T,C> extends Comparable<GrammarElement<T, C>> {

    /**
     * Searches for the element in the string expression and returns a matcher.
     * Because an expression is parsed left-to-right the {@param previousNode}
     * is the {@link GrammarElement} immediately before the expression being
     * parsed. It can be used to influence the working of the evaluator
     * (ie. to recognize a minus as a sign if preceeded by an operator or by
     * by a parenthesis).
     *
     * @param previousNode  the previous parsed GrammarElement or null if none
     * @param expression    the string expression to parse for matches
     */
    GrammarElementMatcher match(final GrammarElement<T,C> previousGrammarElement,
            final String expression);

    /**
     * Evaluates the string {@param value} given the {@param params} and the
     * {@parm context} context.
     * @return an evaluation of the node expressed in the target domain.
     */
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

    /**
     * Returns the type of the element.
     * @see GrammarElementType
     */
    GrammarElementType getType();
}
