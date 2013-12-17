package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import com.fillumina.utils.interpreter.AbstractComparableGrammarElement;
import java.util.Arrays;

/**
 * Matches a name in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastMultiGrammarElement<T,C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String[] names;
    private final int operandsBefore;
    private final int operandsAfter;

    public AbstractFastMultiGrammarElement(
            final int priority,
            final int operandsBefore,
            final int operandsAfter,
            final String ... names) {
        super(priority);
        this.names = names;
        this.operandsBefore = operandsBefore;
        this.operandsAfter = operandsAfter;
    }

    protected abstract boolean isOperator();

    @Override
    public GrammarElementMatcher match(final String expression) {
        for (String name: names) {
            final GrammarElementMatcher matcher = matchName(expression, name);
            if (matcher.isFound()) {
                return matcher;
            }
        }
        return FastGrammarElementMatcher.NOT_FOUND;
    }

    private GrammarElementMatcher matchName(final String expression,
            final String name) {
        final int length = name.length();
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
        return Arrays.toString(names);
    }
}
