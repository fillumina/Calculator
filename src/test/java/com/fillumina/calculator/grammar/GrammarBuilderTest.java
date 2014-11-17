package com.fillumina.calculator.grammar;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.SyntaxErrorException;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarBuilderTest {

    private GrammarBuilder<String,Map<String,String>> builder =
            new GrammarBuilder<>();

    private Calculator<String,Map<String,String>> calc;

    @Test
    public void shouldAddAnIntegerOperand() {
        builder.addIntegerOperand(new Evaluator<String, Map<String, String>>() {

            @Override
            public String evaluate(String value, Map<String, String> context) {
                return "INT=" + value;
            }
        });
        Iterable<GrammarElement<String,Map<String,String>>> grammar =
                builder.buildGrammar();
        calc = new Calculator<>(grammar);
        assertEquals("INT=12", calc.solveSingleValue("12"));
        assertNotRecognized(calc, "XYZ");
    }

    @Test
    public void shouldAddAnDateOperand() {
        builder.addDateOperand("dd/MM/yy",
                new Evaluator<String, Map<String, String>>() {

            @Override
            public String evaluate(String value, Map<String, String> context) {
                return "DATE=" + value;
            }
        });
        Iterable<GrammarElement<String,Map<String,String>>> grammar =
                builder.buildGrammar();
        calc = new Calculator<>(grammar);
        assertEquals("DATE=31/12/14", calc.solveSingleValue("31/12/14"));
        assertNotRecognized(calc, "XYZ");
    }

    @Test
    public void shouldAddAStringOperand() {
        builder.addStringOperand(new Evaluator<String, Map<String, String>>() {

            @Override
            public String evaluate(String value, Map<String, String> context) {
                return "STRING=" + value;
            }
        });
        Iterable<GrammarElement<String,Map<String,String>>> grammar =
                builder.buildGrammar();
        calc = new Calculator<>(grammar);
        assertEquals("STRING='something'", calc.solveSingleValue("'something'"));
    }

    @Test
    public void shouldAddAnBooleanOperand() {
        builder.addBooleanOperand("1", "0");
        Iterable<GrammarElement<String,Map<String,String>>> grammar =
                builder.buildGrammar();
        calc = new Calculator<>(grammar);
        assertEquals("1", calc.solveSingleValue("true"));
        assertNotRecognized(calc, "XYZ");
    }

    private void assertNotRecognized(
            final Calculator<String,Map<String,String>> calc,
            final String expression) {
        try {
            calc.solveSingleValue("XYZ");
            fail(expression + " is recognized");
        } catch (SyntaxErrorException e) {
            //ok
        }
    }
}
