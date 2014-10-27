package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;

/**
 * Base for matching {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractMatchingFastElement<T, C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final int operandsBefore;
    private final int operandsAfter;

    public AbstractMatchingFastElement(final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(priority);
        this.operandsBefore = operandsBefore;
        this.operandsAfter = operandsAfter;
    }

    protected abstract boolean isOperator();

    protected GrammarElementMatcher matchSymbol(final String expression,
            final String symbol) {
        final int length = symbol.length();
        final int expressionLength = expression.length();
        final int difference = expressionLength - length;
        if (difference < 0) {
            return FastElementMatcher.NOT_FOUND;
        }
        int idx = -1;
        while (true) {
            idx = expression.indexOf(symbol, idx + 1);
            if (idx == -1) {
                return FastElementMatcher.NOT_FOUND;
            }
            if (!(length == 1 && isOperator())) {
                // single char operators are allowed not to be surrounded
                // by spaces
                if (idx > 0 &&
                        Character.isAlphabetic(expression.charAt(idx - 1))) {
                    continue;
                }
                if (idx < difference &&
                        Character.isAlphabetic(expression.charAt(idx + length))) {
                    continue;
                }
            }
            return new FastElementMatcher(idx, idx + length);
        }
    }

    @Override
    public int getRequiredOperandsAfter() {
        return operandsAfter;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return operandsBefore;
    }
}
