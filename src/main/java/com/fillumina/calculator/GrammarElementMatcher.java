package com.fillumina.calculator;

/**
 * Returns an eventual match of an element in the string expression.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface GrammarElementMatcher {

    /**
     * Always call this before getting start or end (needed for pattern matcher).
     *
     * @return {@code true} if the element has been found.
     */
    boolean isFound();

    /** The start index at which a match has been found. */
    int getStart();

    /** The end index at which a match has been found. */
    int getEnd();

    GrammarElementMatcher NOT_FOUND = new GrammarElementMatcher() {

        @Override
        public boolean isFound() {
            return false;
        }

        @Override
        public int getStart() {
            throw new UnsupportedOperationException("Not found!");
        }

        @Override
        public int getEnd() {
            throw new UnsupportedOperationException("Not found!");
        }
    };
}
