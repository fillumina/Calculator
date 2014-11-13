package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.StringElement;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractStringElementTest extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return StringElement.INSTANCE;
    }

    @Test
    public void shouldCleanTheEscapedCharactersFromTheString() {
        assertEquals("abcd\\fg",
                StringElement.evaluateString("ab\\cd\\\\fg"));
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
