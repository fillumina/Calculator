package com.fillumina.utils.interpreter.grammar.pattern.instances;

import com.fillumina.utils.interpreter.Calculator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class BooleanGrammarTest {

    private Calculator<Boolean,Map<String,Boolean>> calculator;
    private Map<String,Boolean> context;

    @Before
    public void init() {
        calculator = new Calculator<>(BooleanGrammar.INSTANCE);
        context = new HashMap<>();
    }

    private void assertEvaluateTo(final boolean expected, final String expression) {
        assertEquals("\"" + expression + "\"",
                expected, calculator.solve(expression, context).get(0));
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
        assertEvaluateTo(false, "falseORfalse");
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