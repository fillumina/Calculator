package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractQuotedStringElementTest extends GrammarElementTestBase {

    private static final AbstractQuotedStringOperand<Double,Void>
            QUOTED_STRING_OPERAND = new AbstractQuotedStringOperand<Double,Void>(0) {
        private static final long serialVersionUID = 1L;

        @Override
        public Double evaluate(String value, List<Double> params, Void context) {
            return null;
        }
    };

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return QUOTED_STRING_OPERAND;
    }

    @Test
    public void shouldCleanTheEscapedCharactersFromTheString() {
        assertEquals("abcd\\fg",
                QUOTED_STRING_OPERAND.evaluateString("ab\\cd\\\\fg"));
    }

    @Test
    public void shouldFindTheSingleQuotedString() {
        recognize("'abc def'", "lkdjlk 'abc def' lklkew3 _");
    }

    @Test
    public void shouldRecognizeTheEmptyString() {
        recognize("''", "ljldkjsp''lfkd");
    }

    @Test
    public void shouldRecognizeTheDoubleQuotedString() {
        recognize("\"1234\"", "a;la assl \"1234\"");
    }

    @Test
    public void shouldRecognizeTheStringWithoutSpacesAround() {
        recognize("\"1234\"", "\"1234\"");
    }

    @Test
    public void shouldRecognizeAnEscapedQuote() {
        recognize("'123\\'4'", "  '123\\'4' ");
    }

    @Test
    public void shouldIgnoreSingleQuotesInsideDoubleQuotes() {
        recognize("\"hello 'world' this\"",  " \"hello 'world' this\" is me");
    }

    @Test
    public void shouldIgnoreDoubleQuotesInsideSignleQuotes() {
        recognize("'hello \"world\" this'",  " 'hello \"world\" this' is me");
    }

    @Test
    public void shouldNotRecognizeAnEmptyElement() {
        notRecognize("");
    }

    @Test
    public void shouldNotRecognizeAStringWithoutQuotes() {
        notRecognize("there are no quotes");
    }
}
