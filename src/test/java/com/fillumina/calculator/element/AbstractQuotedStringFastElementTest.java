package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.QuotedStringFastElement;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractQuotedStringFastElementTest extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return QuotedStringFastElement.INSTANCE;
    }

    @Test
    public void shouldCleanTheEscapedCharactersFromTheString() {
        assertEquals("abcd\\fg",
                QuotedStringFastElement.evaluateString("ab\\cd\\\\fg"));
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
