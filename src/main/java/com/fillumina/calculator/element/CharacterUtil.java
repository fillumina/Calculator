package com.fillumina.calculator.element;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class CharacterUtil {

    private CharacterUtil() {}

    private static final FastCharacterChecker ALPHABETIC =
            new FastCharacterChecker(new FastCharacterChecker.Evaluator() {
        @Override
        public boolean evaluate(char c) {
            return Character.isAlphabetic(c);
        }
    });

    private static final FastCharacterChecker WHITESPACE =
            new FastCharacterChecker(new FastCharacterChecker.Evaluator() {
        @Override
        public boolean evaluate(char c) {
            return Character.isWhitespace(c);
        }
    });

    public static boolean isAlphabetic(final char c) {
        return ALPHABETIC.contains(c);
    }

    public static boolean isWhitespace(final char c) {
        return WHITESPACE.contains(c);
    }

    public static boolean isDigit(final char c) {
        return c <= '9' && c >= '0';
    }
}
