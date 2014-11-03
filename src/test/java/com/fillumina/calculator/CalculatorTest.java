package com.fillumina.calculator;

import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.StringEvaluator;
import com.fillumina.calculator.grammar.GrammarBuilder;
import com.fillumina.calculator.grammar.SettableContextedGrammarBuilder;
import com.fillumina.calculator.grammar.StringParametricEvaluator;
import com.fillumina.calculator.grammar.instance.ArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CalculatorTest {

    private Calculator<Double,Map<String,Double>> calculator =
            Calculator.createPruning(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldCalculateASimpleExpression() {
        assertEquals(12.5, calculator.solveSingleValue("(20 + 5)/2 "), 0.001);
    }

    @Test
    public void shouldRequireAVariable() {
        SolutionTree<Double,Map<String,Double>> solutionTree =
                calculator.createSolutionTree("2 * x / 5");
        solutionTree.solve();
        assertFalse(solutionTree.isSolved());

        solutionTree.solve(Mapper.<Double>create("x", 10.0d));

        assertEquals(4.0, (double)solutionTree.getSingleSolution(), 0.0001);
    }

    @Test
    public void shouldDefineAndUseASimpleGrammar() {
        final Calculator<Integer,Void> calc = new Calculator<>(
            new GrammarBuilder<Integer,Void>()
                .addIntOperand(new StringEvaluator<Integer, Void>() {
                        @Override
                        public Integer evaluate(String value, Void context) {
                            return Integer.valueOf(value);
                        }
                    })
                .addConstant("hundred", 100)
                .addOperator()
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .symbols("+")
                    .evaluator(new StringParametricEvaluator<Integer, Void>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Void context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .addOperator()
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .symbols("-")
                    .evaluator(new StringParametricEvaluator<Integer, Void>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Void context) {
                                return params.get(0) - params.get(1);
                            }
                        })
                    .buildOperator()
                .buildGrammar());
        assertEquals(109.0, calc.solveSingleValue("15+hundred-6"), 0);
    }

    @Test
    public void shouldDefineAndUseASimpleGrammarWithContext() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntOperand(new StringEvaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .addConstant("hundred", 100)
                .addOperator()
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .symbols("+")
                    .evaluator(new StringParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildGrammar());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        assertEquals(1121.0,
                calc.solveSingleValue(context, "15 + (hundred + 6) + thousand"), 0);
    }

    @Test
    public void shouldDefineAndUseASimpleGrammarWithContextAndSetAVariable() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addIntOperand(new StringEvaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .addConstant("hundred", 100)
                .addOperator()
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .symbols("+")
                    .evaluator(new StringParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildGrammar());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        assertEquals(1121.0,
                calc.solveSingleValue(context, "(x = 15 + hundred) + 6 + thousand"), 0);

        assertEquals(115, context.get("x"), 0);
    }

    @Test
    public void shouldShadowALegitimateConstant() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addIntOperand(new StringEvaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .addConstant("hundred", 100)
                .addOperator()
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .symbols("+")
                    .evaluator(new StringParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildGrammar());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        assertEquals(1121.0,
                calc.solveSingleValue(context, "(x = 15 + hundred) + 6 + thousand"), 0);

        assertEquals(115, context.get("x"), 0);
    }
}
