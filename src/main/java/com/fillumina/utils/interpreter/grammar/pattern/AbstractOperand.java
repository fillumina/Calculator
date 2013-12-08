package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractOperand<T,C>
        extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    /**
     * DECIMAL in scientific notation regular expression.
     * The problem here is that the - and + symbols shouldn't be included
     * in the number if there is a digit before (eventually separated by spaces)
     * see http://www.regular-expressions.info/refadv.html
     *
     * (\\D\\ {100}) is wrong but works recognizing non digit chars
     */
    public static final String SCIENTIFIC_NOTATION_NUMBER_REGEXP =
            "((?<=(([\\*\\+\\-/^]\\ {0,100})|" +
                "(\\D\\ {100})|(\\(\\ {0,100})))[\\+\\-])?" +
                "\\d+(\\.\\d+)?([Ee][\\+\\-]?\\d+)?";

    public AbstractOperand(final String symbolRegexp, final int priority) {
        super(symbolRegexp, priority);
    }

    @Override
    public T evaluate(final String value,
            final List<T> params, final C context) {
        return evaluate(value, context);
    }

    public abstract T evaluate(final String value, final C context);

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERAND;
    }
}
