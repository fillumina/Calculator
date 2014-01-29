package com.fillumina.utils.interpreter.grammar.pattern;

import static com.fillumina.utils.interpreter.util.PatternBuilder.p;
import static com.fillumina.utils.interpreter.util.PatternBuilder.p_or;
import static com.fillumina.utils.interpreter.util.PatternBuilder.p_group;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractDoublePatternElement<T,C>
        extends AbstractOperand<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractDoublePatternElement(final int priority) {
        super(getDoublePattern(), priority);
    }

    /*
     * DECIMAL in scientific notation regular expression.
     * The problem here is that the - and + symbols shouldn't be included
     * in the number if there is a digit before (eventually separated by spaces)
     * see http://www.regular-expressions.info/refadv.html
     *
     * (\\D\\ {100}) is wrong but works recognizing non digit chars
     */
    public static String getDoublePattern() {
        return p()
            // the signum
            .eventually(
                p().positiveLookBehind(
                    p_or(
                        // 4 + -3
                        p_group().opt('*', '+', '-', '/', '^')
                            .whitespace().repeate(0, 100),
                        // (-2
                        p_group().c('(').whitespace().repeate(0, 100)
                    )
                ).opt('+', '-')
            )

            // the decimal number
            .digit().many()
            .c('.').eventually()
            .digit().atLeastOnce()

            // the exponent
            .eventually(
                    p()
                        .opt('E', 'e')
                        .opt('+', '-').eventually()
                        .digit().atLeastOnce()
            )
            .toString();
    }
}