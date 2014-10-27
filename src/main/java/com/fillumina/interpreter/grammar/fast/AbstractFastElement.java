package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.GrammarElementMatcher;

/**
 * Matches a name in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastElement<T,C>
        extends AbstractMatchingFastElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String symbol;

    public AbstractFastElement(final String symbol,
            final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(priority, operandsBefore, operandsAfter);
        this.symbol = symbol;
    }

    @Override
    public GrammarElementMatcher match(final String expression) {
        return matchSymbol(expression, symbol);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
