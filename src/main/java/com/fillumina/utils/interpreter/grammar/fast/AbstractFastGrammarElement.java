package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;

/**
 * Matches a name in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastGrammarElement<T,C>
        extends AbstractFastMatchingGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String name;

    public AbstractFastGrammarElement(final String name,
            final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(priority, operandsBefore, operandsAfter);
        this.name = name;
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        return matchName(expression, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
