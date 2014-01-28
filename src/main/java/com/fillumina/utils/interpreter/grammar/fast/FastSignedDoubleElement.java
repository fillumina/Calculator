package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.AbstractComparableGrammarElement;

/**
 * Eats the signum whatever the conditions.
 * 
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastSignedDoubleElement<T,C> extends FastDoubleElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final AbstractComparableGrammarElement<?,?> INSTANCE =
            new FastSignedDoubleElement<>(0);

    public FastSignedDoubleElement(final int priority) {
        super(priority);
    }

    public FastSignedDoubleElement(final int priority,
            final char decimalSeparator) {
        super(priority, decimalSeparator);
    }

    @Override
    protected boolean isPreceededByASignumAndAnOperatorOrParentheses(
            char[] carray, int start) {
        final char signum = carray[start - 1];
        return (signum == '+' || signum == '-');
    }
}
