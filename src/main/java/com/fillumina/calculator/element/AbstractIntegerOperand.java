package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import static com.fillumina.calculator.element.CharacterUtil.isDigit;
import static com.fillumina.calculator.element.SignumFinder.isPreceededByASignum;

/**
 * Recognizes integers.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractIntegerOperand<T,C> extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractIntegerOperand(final int priority) {
        super(priority);
    }

    @Override
    public GrammarElementMatcher match(
            final GrammarElement<T,C> previousGrammarElement,
            final String expression) {
        final char[] carray = expression.toCharArray();
        int start = findFirstDigitIndex(carray, 0);
        if (start == -1) {
            return ElementMatcher.NOT_FOUND;
        }
        final int length = carray.length;
        int end = length;
        for (int i=start + 1; i<length; i++) {
            char c = carray[i];
            if (!isDigit(c)) {
                end = i;
                break;
            }
        }
        if (start > 0 &&
                isPreceededByASignum(previousGrammarElement, carray, start)) {
            start --; // includes the signum
        }
        return new ElementMatcher(start, end);
    }

    private int findFirstDigitIndex(final char[] carray, final int start) {
        for (int i=start; i<carray.length; i++) {
            if (isDigit(carray[i])) {
                return i;
            }
        }
        return -1;
    }
}
