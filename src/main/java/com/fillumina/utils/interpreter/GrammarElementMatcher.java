package com.fillumina.utils.interpreter;

public interface GrammarElementMatcher {
    /** The element has been found. */
    boolean found();

    /** The start index at which it's been found. */
    int start();

    /** The end index at which it's been found. */
    int end();
}
