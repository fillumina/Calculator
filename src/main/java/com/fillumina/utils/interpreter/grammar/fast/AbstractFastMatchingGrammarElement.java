package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.AbstractComparableGrammarElement;
import com.fillumina.utils.interpreter.GrammarElementMatcher;

/**
 * Base for matching {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastMatchingGrammarElement<T, C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final int operandsBefore;
    private final int operandsAfter;

    public AbstractFastMatchingGrammarElement(final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(priority);
        this.operandsBefore = operandsBefore;
        this.operandsAfter = operandsAfter;
    }

    protected abstract boolean isOperator();

    protected GrammarElementMatcher matchName(final String expression,
            final String name) {
        final int length = name.length();
        int idx = -1;
        while (true) {
            idx = expression.indexOf(name, idx + 1);
            if (idx == -1) {
                return FastGrammarElementMatcher.NOT_FOUND;
            }
            if (!(length == 1 && isOperator())) {
                // single char operators are allowed not to be surrounded
                // by spaces
                if (idx > 0 && isChar(expression.charAt(idx - 1))) {
                    continue;
                }
                if (idx + length < expression.length() &&
                        isChar(expression.charAt(idx + length))) {
                    continue;
                }
            }
            return new FastGrammarElementMatcher(idx, idx + length);
        }
    }

    protected boolean isChar(final char c) {
        return Character.isAlphabetic(c);
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
