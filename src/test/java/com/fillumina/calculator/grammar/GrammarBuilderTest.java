package com.fillumina.calculator.grammar;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.SyntaxErrorException;
import com.fillumina.calculator.element.AbstractDoubleOperand;
import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarBuilderTest {

    private GrammarBuilder<String,Map<String,String>,?> builder =
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
    public void shouldAddAPatternOperand() {
        builder.addPatternElement(0, "abc", GrammarElementType.OPERAND,
                new ParametricEvaluator<String, Map<String, String>>() {

            @Override
            public String evaluate(String value, List<String> params,
                    Map<String, String> context) {
                return "PATTERN=" + value;
            }
        });
        Iterable<GrammarElement<String,Map<String,String>>> grammar =
                builder.buildGrammar();
        calc = new Calculator<>(grammar);
        assertEquals("PATTERN=abc", calc.solveSingleValue("abc"));
        assertNotRecognized(calc, "xyz");
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

    @Test
    public void shouldExtendTheArithmeticGrammar() {
        final Iterable<GrammarElement<Double,Map<String,Double>>> grammar =
            GrammarBuilder.<Double,Map<String,Double>>create()
                .addAll(DoubleArithmeticGrammar.INSTANCE)
                .addOperator()
                    .priority(5) // priority must be high
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .symbols("@")
                    .evaluator(new ParametricEvaluator
                            <Double, Map<String, Double>>() {

                        @Override
                        public Double evaluate(String value,
                                List<Double> params,
                                Map<String, Double> context) {
                            return 1 / params.get(0);
                        }
                    })
                    .buildOperator()
            .buildDefaultGrammar();

        final Calculator<Double,Map<String,Double>> calc =
                new Calculator<>(grammar);

        assertEquals(1d, calc.solveSingleValue("1234 * @1234"), 0);
    }

    @Test
    public void shouldAddSomeGrammarElements() {
        final Iterable<GrammarElement<Double,Map<String,Double>>> grammar =
            GrammarBuilder.<Double,Map<String,Double>>create()
                .addAll(new AbstractDoubleOperand<Double,Map<String,Double>>(0) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Double evaluate(String value, List<Double> params,
                            Map<String, Double> context) {
                        return Double.valueOf(value);
                    }
                })
                .addOperator()
                    .priority(5) // priority must be high
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .symbols("@")
                    .evaluator(new ParametricEvaluator
                            <Double, Map<String, Double>>() {

                        @Override
                        public Double evaluate(String value,
                                List<Double> params,
                                Map<String, Double> context) {
                            return 1 / params.get(0);
                        }
                    })
                    .buildOperator()
            .buildDefaultGrammar();

        final Calculator<Double,Map<String,Double>> calc =
                new Calculator<>(grammar);

        assertEquals(0.5, calc.solveSingleValue("@ 2"), 0);
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
