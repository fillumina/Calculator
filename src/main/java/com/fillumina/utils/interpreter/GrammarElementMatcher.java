package com.fillumina.utils.interpreter;

/**
 * Returns an eventual match of an element in the string expression.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface GrammarElementMatcher {

    /**
     * The element has been found.
     * Always call this before getting start or end (needed for pattern matcher).
     */
    boolean isFound();

    /** The getStart index at which it's been isFound. */
    int getStart();

    /** The getEnd index at which it's been isFound. */
    int getEnd();
}
