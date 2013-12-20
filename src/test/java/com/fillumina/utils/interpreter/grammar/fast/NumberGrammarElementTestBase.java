package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.GrammarElementMatcher;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class NumberGrammarElementTestBase {

    protected abstract GrammarElement<Double, Void> getNumberGrammarElement();

    protected void recognize(final String expected, final String expression) {
        final GrammarElementMatcher matcher = getNumberGrammarElement().
                match(expression);
        if (!matcher.isFound()) {
            throw new AssertionError(expression + " not matches");
        }
        final String result = expression.substring(matcher.getStart(),
                matcher.getEnd());
        assertEquals(expected, result);
    }

    protected void notRecognize(final String expression) {
        final GrammarElementMatcher matcher = getNumberGrammarElement().
                match(expression);
        if (matcher.isFound()) {
            throw new AssertionError("found: '" +
                    expression.substring(matcher.getStart(),
                    matcher.getEnd()) + "'");
        }
    }
}
