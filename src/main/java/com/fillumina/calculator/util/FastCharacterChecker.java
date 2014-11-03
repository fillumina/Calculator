package com.fillumina.calculator.util;

import java.io.Serializable;

/**
 * Check if a char is contained in a String in a very fast way.
 * The check is done in O(1) while the construction
 * is done in O(n) so it is recommended when you need to do
 * many searches (for just one check use
 * {@link String#contains(java.lang.CharSequence) } ).
 * The size is fixed and is 1024 * 8 = 8 Kb.
 *
 * @author fillumina@gmail.com
 */
class FastCharacterChecker implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final FastCharacterChecker EMPTY =
            new FastCharacterChecker() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean contains(final char c) {
            return false;
        }
    };

    public static interface Evaluator {
        boolean evaluate(char c);
    }

    private final long[] positions = new long[1024]; // 65536 / 64 = 1024

    public FastCharacterChecker() {
    }

    public FastCharacterChecker(final String string) {
        this(string.toCharArray());
    }

    public FastCharacterChecker(final char... array) {
        addCharacters(array);
    }

    public FastCharacterChecker(final Evaluator evaluator) {
        for (char c=Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            if (evaluator.evaluate(c)) {
                addCharacter(c);
            }
        }
    }

    private void addCharacters(final char... array) {
        for (final char c: array) {
            addCharacter(c);
        }
    }

    private void addCharacter(final char c) {
        final int index = c >> 6;
        final int value = c - (index << 6);
        positions[index] |= 1L << value;
    }

    public boolean contains(final char c) {
        final int index = c >> 6; // c / 64
        final int value = c - (index << 6); // c - (index * 64)
        return (positions[index] & (1L << value)) != 0;
    }
}
