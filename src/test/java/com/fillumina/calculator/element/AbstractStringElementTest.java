package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractStringElementTest extends GrammarElementTestBase {

    private static final AbstractStringOperand<Double,Void> STRING_OPERAND =
            new AbstractStringOperand<Double,Void>(0) {
        private static final long serialVersionUID = 1L;

        @Override
        public Double evaluate(String value, List<Double> params, Void context) {
            return null;
        }
    };

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return STRING_OPERAND;
    }

    @Test
    public void shouldCleanTheEscapedCharactersFromTheString() {
        assertEquals("abcd\\fg",
                STRING_OPERAND.evaluateString("ab\\cd\\\\fg"));
    }

    @Test
    public void shouldFindTheString() {
        recognize("abc", "  abc  ");
    }

    @Test
    public void shouldRecognizeAString() {
        recognize("abc", "abc");
    }

    @Test
    public void shouldNotRecognizeTheEmptyString() {
        notRecognize("");
    }

    @Test
    public void shouldRecognizeTheFirstString() {
        recognize("123", " 123 abc");
    }

    @Test
    public void shouldRecognizeAnEscapedQuote() {
        recognize("123\\'4", "  123\\'4 ");
    }
}
