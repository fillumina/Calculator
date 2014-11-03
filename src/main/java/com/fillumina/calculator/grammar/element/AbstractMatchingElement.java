package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;

/**
 * Base for matching {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractMatchingElement<T, C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final int operandsBefore;
    private final int operandsAfter;

    public AbstractMatchingElement(final int priority,
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
            return ElementMatcher.NOT_FOUND;
        }
        int idx = -1;
        while (true) {
            idx = expression.indexOf(symbol, idx + 1);
            if (idx == -1) {
                return ElementMatcher.NOT_FOUND;
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
            return new ElementMatcher(idx, idx + length);
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
