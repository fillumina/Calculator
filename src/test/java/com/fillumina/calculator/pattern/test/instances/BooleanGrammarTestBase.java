package com.fillumina.calculator.pattern.test.instances;

import com.fillumina.calculator.Calculator;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class BooleanGrammarTestBase {

    private Calculator<Boolean,Map<String,Boolean>> calculator;
    private Map<String,Boolean> context;

    public abstract Calculator<Boolean,Map<String,Boolean>> getCalculator();

    @Before
    public void init() {
        calculator = getCalculator();
        context = new HashMap<>();
    }

    private void assertEvaluateTo(final boolean expected,
            final String expression) {
        assertEquals("\"" + expression + "\"",
                expected, calculator.solve(context, expression).get(0));
    }

    @Test
    public void shouldRecognizeTrueUppercase() {
        assertEvaluateTo(true, "TRUE");
    }

    @Test
    public void shouldRecognizeTrueLowercase() {
        assertEvaluateTo(true, "true");
    }

    @Test
    public void shouldRecognizeTrueWithSpacesAround() {
        assertEvaluateTo(true, " TRUE  ");
    }

    @Test
    public void shouldRecognizeFalse() {
        assertEvaluateTo(false, "false");
    }

    @Test
    public void shouldRecognizeAnd() {
        assertEvaluateTo(true, "true AND true");
    }

    @Test
    public void shouldRecognizeAnd2() {
        assertEvaluateTo(false, "true AND false");
    }

    @Test
    public void shouldRecognizeAnd3() {
        assertEvaluateTo(false, "false AND true");
    }

    @Test
    public void shouldRecognizeAnd4() {
        assertEvaluateTo(false, "false AND false");
    }

    @Test
    public void shouldRecognizeOr() {
        assertEvaluateTo(true, "true OR true");
    }

    @Test
    public void shouldRecognizeOr2() {
        assertEvaluateTo(true, "true OR false");
    }

    @Test
    public void shouldRecognizeOr3() {
        assertEvaluateTo(true, "false OR true");
    }

    @Test
    public void shouldRecognizeOr4() {
        assertEvaluateTo(false, "false OR false");
    }

    @Test
    public void shouldRecognizeNot() {
        assertEvaluateTo(true, "NOT false");
    }

    @Test
    public void shouldRecognizeNot2() {
        assertEvaluateTo(false, "NOT true");
    }

    @Test
    public void shouldRecognizeTheOperandsWithoutSpaces() {
        assertEvaluateTo(false, "false|false");
    }

    @Test
    public void shouldRecognizeTheExpression() {
        assertEvaluateTo(false, "NOT (false OR(true AND true))");
    }

    @Test
    public void shouldUseTheVariablePassedInTheContext() {
        context.put("x", Boolean.FALSE);
        assertEvaluateTo(false, "NOT (TRUE || x)");
    }
}