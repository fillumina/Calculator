package com.fillumina.calculator.element;

/**
 * Eats the sign eagerly whatever the conditions. To be used in mixed
 * string - number grammars so that the sign could not be confounded
 * with the operation.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractSignedDoubleOperand<T,C>
        extends AbstractDoubleOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractSignedDoubleOperand(final int priority) {
        super(priority);
    }

    public AbstractSignedDoubleOperand(final int priority,
            final char decimalSeparator) {
        super(priority, decimalSeparator);
    }

    @Override
    protected boolean isPreceededByASignumAndAnOperatorOrParentheses(
            char[] carray, int start) {
        final char sign = carray[start - 1];
        return (sign == '+' || sign == '-');
    }
}