package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.util.Arrays;

/**
 * Matches a name in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastMultiGrammarElement<T,C>
        extends AbstractFastMatchingGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String[] names;

    public AbstractFastMultiGrammarElement(final int priority,
            final int operandsBefore,
            final int operandsAfter,
            final String... names) {
        super(priority, operandsBefore, operandsAfter);
        this.names = names;
    }

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

    @Override
    public String toString() {
        return Arrays.toString(names);
    }
}
