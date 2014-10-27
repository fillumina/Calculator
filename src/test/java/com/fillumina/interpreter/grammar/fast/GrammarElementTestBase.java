package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.GrammarElement;
import com.fillumina.interpreter.GrammarElementMatcher;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class GrammarElementTestBase {

    protected abstract GrammarElement<Double, Void> getGrammarElement();

    protected void recognize(final String expected, final String expression) {
        final GrammarElementMatcher matcher = getGrammarElement().
                match(expression);
        if (!matcher.isFound()) {
            throw new AssertionError(expression + " not matches");
        }
        final String result = expression.substring(matcher.getStart(),
                matcher.getEnd());
        assertEquals(expected, result);
    }

    protected void notRecognize(final String expression) {
        final GrammarElementMatcher matcher = getGrammarElement().
                match(expression);
        if (matcher.isFound()) {
            throw new AssertionError("found: '" +
                    expression.substring(matcher.getStart(),
                    matcher.getEnd()) + "'");
        }
    }
}
