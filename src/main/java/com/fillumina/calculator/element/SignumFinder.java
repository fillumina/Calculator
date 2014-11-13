package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;

/**
 * Plus and minus have a double meaning in common arithmetic: they can be
 * both the sign of a number (and thus have high priority over other operators)
 * and a low priority operator. To overcome this problem numbers should find by
 * themselves if they have a sign.
 * The rule is: <b>the sign is always attached to the number if there isn't any
 * space between them and there isn't any digit before it</b>.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class SignumFinder {

    private SignumFinder() {}

    /**
     * This method here is controversial. It is needed to see if the
     * preceeding + or - should be part of the number, but this can be
     * determined only in a standard arithmetic with some checks. I just
     * assume here that the grammar defined is somewhat 'close' to the
     * standard arithmetic.
     */
    static <T,C> boolean isPreceededByASignum(
            final GrammarElement<T,C> previusGrammarElement,
            final char[] carray,
            final int start) {
        assert start > -1;
        final char sign = carray[start - 1];
        if (sign == '+' || sign == '-') {
            if (start > 1) {
                char c;
                for (int i=start -2; i>=0; i--) {
                    c = carray[i];
                    // would be already parsed by a previous application
//                    if (CharacterUtil.isDigit(c)) {
//                        return false;
//                    }
                    if (CharacterUtil.isAlphabetic(c)) {
                        return false;
                    } else if (CharacterUtil.isWhitespace(c)) {
                        // ignore
                    } else {
                        // operator?
                        return true;
                    }
                }
            }

            if (previusGrammarElement != null) {
                switch (previusGrammarElement.getType()) {
                    case UNRECOGNIZED:
                    case WHITE_SPACE:
                    case CLOSED_PAR:
                    case OPERAND:
                        return false;

                    case OPEN_PAR:
                    case OPERATOR:
                        return true;
                }
            }

            return true;
        }
        return false;
    }
}
