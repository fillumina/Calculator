package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.StringFastElement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractStringFastElementTest extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return StringFastElement.INSTANCE;
    }

    @Test
    public void shouldCleanTheEscapedCharactersFromTheString() {
        assertEquals("abcd\\fg",
                StringFastElement.evaluateString("ab\\cd\\\\fg"));
    }

    @Test
    public void shouldFindTheString() {
        recognize("abc", "  abc  ");
    }

    @Test
    public void shouldNotRecognizeTheEmptyString() {
        notRecognize("");
    }

    @Test
    public void shouldRecognizeAnEscapedQuote() {
        recognize("123\\'4", "  123\\'4 ");
    }
}
