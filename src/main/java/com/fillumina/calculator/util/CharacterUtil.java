package com.fillumina.calculator.util;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CharacterUtil {
    private static FastCharacterChecker characters;
    static {
        FastCharacterChecker.Appendable builder =
                new FastCharacterChecker.Appendable();
        for (char c=Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            if (Character.isAlphabetic(c)) {
                builder.addCharacter(c);
            }
        }
        characters = builder;
    }

    public static boolean isNotAlpha(final char c) {
        //return !characters.contains(c);
        return c < 'A' || (c > 'Z' && c < 'a') || c > 'z';
    }

    public static boolean isDigit(final char c) {
        return c <= '9' && c >= '0';
    }
}
