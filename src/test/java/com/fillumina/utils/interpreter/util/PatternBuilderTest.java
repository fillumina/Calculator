package com.fillumina.utils.interpreter.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.fillumina.utils.interpreter.util.PatternBuilder.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBuilderTest {

    @Test
    public void shouldCreateAnOption() {
        assertPattern("[abcd]",
                p().opt('a', 'b', 'c', 'd'));
    }

    @Test
    public void shouldCreateANegateOption() {
        assertPattern("[^abcd]",
                p().nopt('a', 'b', 'c', 'd'));
    }

    @Test
    public void shouldCreateAnOptionWithMany() {
        assertPattern("[abcd]*",
                p().opt('a', 'b', 'c', 'd').many());
    }

    @Test
    public void shouldCreateAnOptionWithAtLeastOnce() {
        assertPattern("[abcd]+",
                p().opt('a', 'b', 'c', 'd').atLeastOnce());
    }

    @Test
    public void shouldCreateAnOptionWithEventually() {
        assertPattern("[abcd]?",
                p().opt('a', 'b', 'c', 'd').eventually());
    }

    @Test
    public void shouldCreateAnPatternOptions() {
        assertPattern("([ab]|[cd])",
                p().or(p().opt('a', 'b'), p().opt('c', 'd')) );
    }

    @Test
    public void shouldCreateAComplexPattern() {
        assertPattern("([\\*\\+\\-/\\^][\\t\\n\\ ]{0,100})",
                p().group(
                        p().opt('*', '+', '-', '/', '^')
                            .opt('\t', '\n', ' ').repeate(0, 100)
                )
        );
    }

    @Test
    public void shouldCreateAPositiveLookAround() {
        assertPattern("q(?=u)",
                p('q').positiveLookAhead(p('u'))
        );
    }

    private static final String OP_WS = "([\\*\\+\\-/\\^][\\t\\n\\ ]{0,100})";
    @Test
    public void shouldGetOP_WS() {
        assertPattern(OP_WS,
                p_group().opt('*', '+', '-', '/', '^')
                    .opt('\t', '\n', ' ').repeate(0, 100)
            );
    }

    private static final String NO_DIGIT = "(\\D\\ {100})";
    @Test
    public void shouldGetNO_DIGIT() {
        assertPattern(NO_DIGIT,
                p_group().notDigit().c(' ').repeateExactly(100)
            );
    }

    private static final String SPACES = "(\\(\\ {0,100})";
    @Test
    public void shouldGetSPACES() {
        assertPattern(SPACES,
                p_group().c('(').c(' ').repeate(0, 100)
            );
    }

    private static final String INNER_POS_LOOK_BEHIND = "(" + OP_WS + "|" +
                NO_DIGIT + "|" + SPACES + ")";
    @Test
    public void shouldGetINNER_POS_LOOK_BEHIND() {
        assertPattern(INNER_POS_LOOK_BEHIND,
                p_or(
                    p_group().opt('*', '+', '-', '/', '^')
                        .opt('\t', '\n', ' ').repeate(0, 100),
                    p_group().notDigit().c(' ').repeateExactly(100),
                    p_group().c('(').c(' ').repeate(0, 100)
                )
            );
    }

    private static final String LOOK_BEHIND = "(?<=" + INNER_POS_LOOK_BEHIND + ")";
    @Test
    public void shouldGetLOOK_BEHIND() {
        assertPattern(LOOK_BEHIND,
                p().positiveLookBehind(
                    p_or(
                        p_group().opt('*', '+', '-', '/', '^')
                            .opt('\t', '\n', ' ').repeate(0, 100),
                        p_group().notDigit().c(' ').repeateExactly(100),
                        p_group().c('(').c(' ').repeate(0, 100)
                    )
                )
            );
    }

    private static final String LEAVE_THE_SIGN = "(" + LOOK_BEHIND + "[\\+\\-])?";
    @Test
    public void shouldGetLEAVE_THE_SIGN() {
        assertPattern(LEAVE_THE_SIGN,
                p().group(
                    p().positiveLookBehind(
                        p_or(
                            p_group().opt('*', '+', '-', '/', '^')
                                .opt('\t', '\n', ' ').repeate(0, 100),
                            p_group().notDigit().c(' ').repeateExactly(100),
                            p_group().c('(').c(' ').repeate(0, 100)
                        )
                    ).opt('+', '-')
                ).eventually()
            );
    }

    private static final String DECIMAL_NUMBER = "\\d+(\\.\\d+)?";
    @Test
    public void shouldGetDECIMAL_NUMBER() {
        assertPattern(DECIMAL_NUMBER,
                p().digit().atLeastOnce().group(
                        p('.').digit().atLeastOnce()
                ).eventually()
        );
    }

    private static final String EXPONENT = "([Ee][\\+\\-]?\\d+)?";
    @Test
    public void shouldGetEXPONENT() {
        assertPattern(EXPONENT,
                p().group(
                        p().opt('E', 'e').opt('+', '-').eventually()
                            .digit().atLeastOnce()
                ).eventually()
            );
    }

    private static final String SCIENTIFIC =
            LEAVE_THE_SIGN + DECIMAL_NUMBER + EXPONENT;
    @Test
    public void shouldGetSCIENTIFIC() {
        assertPattern(SCIENTIFIC,
                p().group(
                    p().positiveLookBehind(
                        p_or(
                            p_group().opt('*', '+', '-', '/', '^')
                                .opt('\t', '\n', ' ').repeate(0, 100),
                            p_group().notDigit().c(' ').repeateExactly(100),
                            p_group().c('(').c(' ').repeate(0, 100)
                        )
                    ).opt('+', '-')
                ).eventually()

                .digit().atLeastOnce().group(
                        p('.').digit().atLeastOnce()
                ).eventually()

                .group(
                        p().opt('E', 'e').opt('+', '-').eventually()
                            .digit().atLeastOnce()
                ).eventually()
            );
    }

    private static final String SCIENTIFIC_NOTATION_NUMBER_REGEXP =
            "((?<=(([\\*\\+\\-/\\^][\\t\\n\\ ]{0,100})|" +
                "(\\D\\ {100})|(\\(\\ {0,100})))[\\+\\-])?" +
                "\\d+(\\.\\d+)?([Ee][\\+\\-]?\\d+)?";

    @Test
    public void shouldThePartsBeEqualsToTheFullScientificNotationNumberExp() {
        assertEquals(SCIENTIFIC_NOTATION_NUMBER_REGEXP, SCIENTIFIC);
    }

    private void assertPattern(final String expected, final PatternBuilder pb) {
        final String result = pb.toString();
        assertEquals(result, expected, result);
    }
}
