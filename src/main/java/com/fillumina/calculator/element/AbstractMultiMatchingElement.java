package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import java.util.Arrays;

/**
 * Matches a symbol in the given expression using a fast string search
 * algorithm and then validates the found substring. It's about 20% faster
 * than pattern search.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractMultiMatchingElement<T,C>
        extends AbstractMatchingElement<T, C> {
    private static final long serialVersionUID = 1L;

    private final String[] symbols;

    public AbstractMultiMatchingElement(final int priority,
            final int operandsBefore,
            final int operandsAfter,
            final String... symbols) {
        super(priority, operandsBefore, operandsAfter);
        this.symbols = symbols;
    }

    @Override
    public GrammarElementMatcher match(
            final GrammarElement<T,C> previousGrammarElement,
            final String expression) {
        for (String symbol: symbols) {
            final GrammarElementMatcher matcher = matchSymbol(expression, symbol);
            if (matcher.isFound()) {
                return matcher;
            }
        }
        return ElementMatcher.NOT_FOUND;
    }

    @Override
    public String toString() {
        return Arrays.toString(symbols);
    }
}
