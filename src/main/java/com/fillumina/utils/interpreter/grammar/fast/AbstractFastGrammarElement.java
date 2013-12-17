package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import com.fillumina.utils.interpreter.grammar.pattern.AbstractComparableGrammarElement;
import com.fillumina.utils.interpreter.grammar.pattern.FastGrammarElementMatcher;

/**
 * Matches a name in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastGrammarElement<T,C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int length;
    private final int operandsBefore;
    private final int operandsAfter;

    public AbstractFastGrammarElement(final String name,
            final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(priority);
        this.name = name;
        this.length = name.length();
        this.operandsBefore = operandsBefore;
        this.operandsAfter = operandsAfter;
    }

    protected abstract boolean isOperator();

    @Override
    public GrammarElementMatcher match(final String expression) {
        int idx = -1;
        while(true) {
            idx = expression.indexOf(name, idx + 1);
            if (idx == -1) {
                return FastGrammarElementMatcher.NOT_FOUND;
            }
            if (!(length == 1 && isOperator())) {
                if (idx > 0 && isChar(expression.charAt(idx - 1))) {
                    continue;
                }
                if (idx + length < expression .length() &&
                        isChar(expression.charAt(idx + length))) {
                    continue;
                }
            }
            return new FastGrammarElementMatcher(idx, idx + length);
        }
    }

    private boolean isChar(final char c) {
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

    @Override
    public String toString() {
        return name;
    }
}
