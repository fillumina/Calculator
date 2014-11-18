package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementType;
import java.util.List;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractPatternElementTest extends GrammarElementTestBase {
    private static final String PATTERN = "ab+c";

    private static final AbstractPatternElement<Double,Void> PATTERN_OPERATOR =
            new AbstractPatternElement<Double, Void>(0, PATTERN) {
        private static final long serialVersionUID = 1L;

        @Override
        public Double evaluate(String value, List<Double> params, Void context) {
            return Double.valueOf(value);
        }

        @Override
        public GrammarElementType getType() {
            return GrammarElementType.OPERAND;
        }
    };

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return PATTERN_OPERATOR;
    }

    @Test
    public void shoudRecognizeTheGivenPattern() {
        recognize("abbbc", "abbbc");
    }

    @Test
    public void shouldNotRecognizeTheWrongPattern() {
        notRecognize("ac");
    }

    @Test
    public void shouldReturnTheRegexp() {
        assertEquals(PATTERN, PATTERN_OPERATOR.getRegexp());
    }

    @Test
    public void shouldReturnThePattern() {
        assertTrue(PATTERN_OPERATOR.getPattern().matcher("abbbbc").matches());
    }

    @Test
    public void shouldMatchTheExactText() {
        final String regexp = AbstractPatternElement.transform("aba");
        Pattern pattern = Pattern.compile(regexp);
        assertTrue(pattern.matcher("aba").matches());
        assertFalse(pattern.matcher("baba").matches());
    }
}
