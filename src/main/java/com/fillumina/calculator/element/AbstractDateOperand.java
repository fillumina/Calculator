package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import static com.fillumina.calculator.element.CharacterUtil.isAlphabetic;
import static com.fillumina.calculator.element.CharacterUtil.isDigit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Recognizes dates according to the specified pattern. The pattern can
 * contain any type of alphabetic characters, these are not important, only
 * the not-digit, not-alpha characters are taken.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractDateOperand<T,C> extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    private final char[] cpattern;
    private final int firstNonDigitPosition;
    private final char firstNonDigitChar;
    final DateFormat dateFormat;

    /**
     * @param priority  the priority at which this operand is evaluated
     *                  (usually 0)
     * @param pattern   Something of type "dd/MM/yy HH:mm:ss" or "dd/MM/yy"
     *                  see {@link DateFormat}.
     */
    public AbstractDateOperand(final int priority, final String pattern) {
        super(priority);
        this.dateFormat = new SimpleDateFormat(pattern);
        this.cpattern = pattern.toCharArray();
        this.firstNonDigitPosition = getFirstNonDigitPosition(cpattern);
        this.firstNonDigitChar = firstNonDigitPosition == -1 ?
                ' ' : cpattern[firstNonDigitPosition];
    }

    @Override
    public GrammarElementMatcher match(
            final GrammarElement<T,C> previousGrammarElement,
            final String expression) {
        final int startPos = getStartingPos(expression);
        if (startPos == -1) {
            // fast fail
            return ElementMatcher.NOT_FOUND;
        }
        final char[] cpattern = this.cpattern; // cache locally
        final char[] carray = expression.toCharArray();
        int patternIndex = 0;
        int start = -1;
        final int plength = cpattern.length;
        final int clength = carray.length;
        char c;
        for (int i=startPos; i<clength; i++) {
            c = carray[i];
            if (patternIndex == plength) {
                return new ElementMatcher(start, i);
            } else if (isDigit(c)) {
                if (isAlphabetic(cpattern[patternIndex])) {
                    patternIndex++;
                    if (start == -1) {
                        start = i;
                    }
                } else {
                    break;
                }
            } else if (cpattern[patternIndex] == c) {
                patternIndex++;
            } else if (cpattern[patternIndex + 1] == c) {
                patternIndex += 2;
            } else {
                patternIndex = 0;
            }
        }
        if (patternIndex == cpattern.length ||
                (cpattern[patternIndex] == cpattern[patternIndex - 1] &&
                patternIndex + 1 == cpattern.length) ) {
            return new ElementMatcher(start, carray.length);
        }
        return ElementMatcher.NOT_FOUND;
    }

    /** Helper to evaluate a {@link Date} starting from the given pattern. */
    protected Date evaluateDate(final String value) {
        try {
            return dateFormat.parse(value);
        } catch (final ParseException ex) {
            return null;
        }
    }

    private int getStartingPos(final String expression) {
        if (firstNonDigitPosition == -1) {
            return 0; // the pattern have only digits
        }
        final int pos = expression.indexOf(firstNonDigitChar);
        if (pos == -1) {
            return -1; // the expression doesn't contain a valid date
        }
        final int startingPos = pos - firstNonDigitPosition;
        return startingPos > 0 ? startingPos : 0;
    }

    private static int getFirstNonDigitPosition(final char[] cpattern) {
        for (int i=0; i<cpattern.length; i++) {
            final char c = cpattern[i];
            if (!isDigit(c) && !isAlphabetic(c)) {
                return i;
            }
        }
        return -1;
    }
}
