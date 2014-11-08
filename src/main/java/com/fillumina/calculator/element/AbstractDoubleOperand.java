package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import static com.fillumina.calculator.element.CharacterUtil.isDigit;
import java.text.DecimalFormatSymbols;

/**
 * Parse floating point numbers trying to establish if the eventual preceeding
 * sign is part of it.
 *
 * @see AbstractSignedDoubleOperand
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractDoubleOperand<T,C> extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    private final char decimalSeparator;

    public AbstractDoubleOperand(final int priority) {
        this(priority, DecimalFormatSymbols.getInstance().getDecimalSeparator());
    }

    public AbstractDoubleOperand(final int priority,
            final char decimalSeparator) {
        super(priority);
        this.decimalSeparator = decimalSeparator;
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        final char[] carray = expression.toCharArray();

        int start = 0;
        boolean separator = false;
        boolean atLeastOneDigit = false;
        char c;
        FIND_FIRST_DIGIT: while (true) {
            for (int i=0; i<carray.length; i++) {
                c = carray[i];
                if (isDigit(c)) {
                    start = i;
                    atLeastOneDigit = true;
                    break FIND_FIRST_DIGIT;
                }
                if (c == decimalSeparator) {
                    start = i;
                    separator = true;
                    break FIND_FIRST_DIGIT;
                }
            }
            return ElementMatcher.NOT_FOUND;
        }

        boolean exp = false;
        int end = carray.length;
        FOR: for (int i=start + 1; i<end; i++) {
            c = carray[i];
            if (!isDigit(c)) {
                if (c == decimalSeparator) {
                    if (separator) {
                        end = i;
                        break;
                    }
                    separator = true;
                } else {
                    switch (c) {
                        case 'E':
                        case 'e':
                            if (exp) {
                                end = i;
                                break FOR;
                            }
                            exp = true;
                            break;

                        case '+':
                        case '-':
                            final char prevc = carray[i-1];
                            if (prevc == 'e' || prevc == 'E') {
                                break;
                            }

                        default:
                            if (atLeastOneDigit) {
                                end = i;
                                break FOR;
                            } else {
                                start = i;
                            }
                    }
                }
            } else {
                atLeastOneDigit = true;
            }
        }
        final char last = carray[end - 1];
        if (last == 'e' || last == 'E' || last == decimalSeparator) {
            end--;
            if (start == end) {
                return ElementMatcher.NOT_FOUND;
            }
        }
        if (start > 0 &&
                isPreceededByASignumAndAnOperatorOrParentheses(carray, start)) {
            start --; // includes the signum
        }
        return new ElementMatcher(start, end);
    }

    /**
     * This method here is controversial. It is needed to see if the
     * preceeding + or - should be part of the number, but this can be
     * determined only in a standard arithmetic with some checks. I just
     * assume here that the grammar defined is somewhat 'close' to the
     * standard arithmetic.
     */
    protected boolean isPreceededByASignumAndAnOperatorOrParentheses(
            final char[] carray,
            final int start) {
        final char sign = carray[start - 1];
        if (sign == '+' || sign == '-') {
            for (int j = start - 2; j >= 0; j--) {
                final char cj = carray[j];
                if (cj == '*' || cj == '/' || cj == '(' || cj == '+' ||
                        cj == '-' || cj == '^') {
                    return true;
                } else if (cj != ' ' && cj != '\n' && cj != '\t') {
                    return false;
                }
            }
        }
        return false;
    }
}
