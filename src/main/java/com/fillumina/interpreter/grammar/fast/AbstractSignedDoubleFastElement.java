package com.fillumina.interpreter.grammar.fast;

/**
 * Eats the sign eagerly whatever the conditions. To be used in mixed
 * string - number grammars so that the sign could not be confounded
 * with the operation.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractSignedDoubleFastElement<T,C>
        extends AbstractDoubleFastElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractSignedDoubleFastElement(final int priority) {
        super(priority);
    }

    public AbstractSignedDoubleFastElement(final int priority,
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
