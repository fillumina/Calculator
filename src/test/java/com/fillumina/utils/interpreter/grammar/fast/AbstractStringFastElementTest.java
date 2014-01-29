package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.StringFastElement;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

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
}
