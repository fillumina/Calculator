package com.fillumina.utils.interpreter.grammar.pattern;

import static com.fillumina.utils.interpreter.util.PatternBuilder.p;
import static com.fillumina.utils.interpreter.util.PatternBuilder.p_or;
import static com.fillumina.utils.interpreter.util.PatternBuilder.p_par;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternGrammarElement<T,C> extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    /*
     * DECIMAL in scientific notation regular expression.
     * The problem here is that the - and + symbols shouldn't be included
     * in the number if there is a digit before (eventually separated by spaces)
     * see http://www.regular-expressions.info/refadv.html
     *
     * (\\D\\ {100}) is wrong but works recognizing non digit chars
     */

    public static final DoublePatternGrammarElement<?,?> INSTANCE =
            new DoublePatternGrammarElement<>(0);

    public DoublePatternGrammarElement(final int priority) {
        super(getDoublePattern(), priority);
    }

    public static String getDoublePattern() {
        return p()
            // the signum
            .par(
                p().positiveLookBehind(
                    p_or(
                        // 4 + 3
                        p_par().opt('*', '+', '-', '/', '^')
                            .whitespace().repeate(0, 100),
                        // (-2
                        p_par().c('(').whitespace().repeate(0, 100)
                    )
                ).opt('+', '-')
            ).eventually()

            // the decimal number
            .digit().many()
            .c('.').eventually()
            .digit().atLeastOnce()

            // the exponent
            .par(
                    p()
                        .opt('E', 'e')
                        .opt('+', '-').eventually()
                        .digit().atLeastOnce()
            ).eventually()
            .toString();
    }

    /** Override if you need something different from {@code double}. */
    @Override
    @SuppressWarnings("unchecked")
    public T evaluate(final String value, final C context) {
        return (T) Double.valueOf(value);
    }
}
