package com.fillumina.utils.interpreter;

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
