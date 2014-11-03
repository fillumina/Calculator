package com.fillumina.calculator.pattern;

/**
 * Uses for the name of the operator a regexp that rejects characters at
 * the beginning or at the end of the selection so to avoid problems with
 * variables or constants (or possibly other elements) having an operator name
 * inside their names. i.e.: sin -> asino
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperatorSymbol<T,C> extends AbstractOperator<T,C> {
    private static final long serialVersionUID = 1L;

    /**
     * Insert the symbol of the operator, NOT the regular expression.
     * i.e. "*", not "\\*"
     */
    public AbstractOperatorSymbol(final String symbol,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(transform(symbol),
                priority,
                requiredOperandsBefore,
                requiredOperandsAfter);
    }
}
