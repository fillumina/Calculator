package com.fillumina.calculator.util;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CharacterUtil {
    private static final FastCharacterChecker CHARACTERS =
            new FastCharacterChecker(new FastCharacterChecker.Evaluator() {
        @Override
        public boolean evaluate(char c) {
            return Character.isAlphabetic(c);
        }
    });

    public static boolean isNotAlpha(final char c) {
        return !CHARACTERS.contains(c);
        //return c < 'A' || (c > 'Z' && c < 'a') || c > 'z';
    }

    public static boolean isDigit(final char c) {
        return c <= '9' && c >= '0';
    }
}
