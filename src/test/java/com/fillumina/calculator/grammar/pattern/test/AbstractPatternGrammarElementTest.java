package com.fillumina.calculator.grammar.pattern.test;

import static com.fillumina.calculator.grammar.element.AbstractPatternElement.NOT_ENDING_WITH_ALPHA;
import static com.fillumina.calculator.grammar.element.AbstractPatternElement.NOT_STARTING_WITH_ALPHA;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractPatternGrammarElementTest {

    @Test
    public void shouldDetectTheOperator() {
        final Pattern pattern = Pattern.compile(NOT_STARTING_WITH_ALPHA + "/");
        final Matcher matcher = pattern.matcher("1/2");
        assertTrue(matcher.find());
        assertEquals(1, matcher.start());
    }

    @Test
    public void shouldDetectTheOperatorAtTheBeginning() {
        final Pattern pattern = Pattern.compile(NOT_STARTING_WITH_ALPHA + "/");
        final Matcher matcher = pattern.matcher("/2");
        assertTrue(matcher.find());
        assertEquals(0, matcher.start());
    }

    @Test
    public void shouldNotDetectTheOperator() {
        final Pattern pattern = Pattern.compile(NOT_STARTING_WITH_ALPHA + "sin");
        final Matcher matcher = pattern.matcher("asin(1/2)");
        assertFalse(matcher.find());
    }

    @Test
    public void shouldDetectASingleCharOperator() {
        final Pattern pattern = Pattern.compile("\\*");
        final Matcher matcher = pattern.matcher("x*2");
        assertTrue(matcher.find());
        assertEquals(1, matcher.start());
    }

    @Test
    public void shouldNotDetectAWronglyEndingOperator() {
        final String regexp = NOT_STARTING_WITH_ALPHA +
                Pattern.quote("sin") +
                NOT_ENDING_WITH_ALPHA;
        final Pattern pattern = Pattern.compile(regexp);

        assertFalse(pattern.matcher("sino(2*cos(x-3))").find());
        assertTrue(pattern.matcher(" sin(x)").find());
    }
}
