package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Faster than {@link AbstractOperatorName} but it cannot use REGEXP.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractFastOperator<T,C>
        extends AbstractFastGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractFastOperator(final String symbol,
            final int priority,
            final int operandsBefore,
            final int operandsAfter) {
        super(symbol, priority, operandsBefore, operandsAfter);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }

    @Override
    protected boolean isOperator() {
        return true;
    }
}
