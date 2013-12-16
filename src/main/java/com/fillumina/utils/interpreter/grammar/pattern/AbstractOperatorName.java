package com.fillumina.utils.interpreter.grammar.pattern;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperatorName<T,C> extends AbstractOperator<T,C> {
    private static final long serialVersionUID = 1L;

    /**
     * Insert the name of the operator, NOT the regular expression.
     * i.e. "*", not "\\*"
     */
    public AbstractOperatorName(final String name,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(transform(name),
                priority,
                requiredOperandsBefore,
                requiredOperandsAfter);
    }
}
