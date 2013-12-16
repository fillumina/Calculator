package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * An operator is an evaluable element that might expects some arguments.
 * The argument's number defines only the maximum allowable, the actual number
 * depends on the effective availability. So there may be less arguments than
 * required.
 *
 * @param <T> the base element type this operator work on
 * @param <C> the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperator<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    private final int requiredOperandsBefore;
    private final int requiredOperandsAfter;

    /**
     * Defines an Operator by a regular expression.
     * i.e.:
     * <br />
     * <code>
     * new AbstractOperator("sin", 1, 0, 1);
     * </code>
     * <br />
     * defines a sine usable like that: <code>sin(x + 2)</code>.
     *
     * @param name              expression that recognizes the operator
     * @param priority                  priority in respect to other operators
     * @param requiredOperandsBefore    how many operands expected before the
     *                                  operator
     * @param requiredOperandsAfter     how many operands expected after the
     *                                  operator
     */
    public AbstractOperator(final String name,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(name, priority);
        this.requiredOperandsBefore = requiredOperandsBefore;
        this.requiredOperandsAfter = requiredOperandsAfter;
    }

    @Override
    public int getRequiredOperandsAfter() {
        return requiredOperandsAfter;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return requiredOperandsBefore;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }
}
