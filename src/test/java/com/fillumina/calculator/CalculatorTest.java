package com.fillumina.calculator;

import com.fillumina.calculator.element.WhiteSpace;
import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.GrammarBuilder;
import com.fillumina.calculator.grammar.SettableContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.ParametricEvaluator;
import com.fillumina.calculator.instance.ArithmeticGrammar;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
// TODO make this a sort of tutorial or create one
public class CalculatorTest {

    private Calculator<Double,Map<String,Double>> calculator =
            new Calculator<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldCalculateASimpleExpression() {
        assertEquals(12.5, calculator.solveSingleValue("(20 + 5)/2 "), 0.001);
    }

    @Test(expected=ContextException.class)
    public void shouldRequireAVariable() {
        SolutionTree<Double,Map<String,Double>> solutionTree =
                calculator.createSolutionTree("2 * x / 5");
        solutionTree.solve();
    }

    @Test
    public void shouldDefineAndUseASimpleGrammar() {
        final Calculator<Integer,Void> calc = new Calculator<>(
            new GrammarBuilder<Integer,Void>()
                .addIntOperand(new Evaluator<Integer, Void>() {
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
                    .evaluator(new ParametricEvaluator<Integer, Void>() {
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
                    .evaluator(new ParametricEvaluator<Integer, Void>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Void context) {
                                return params.get(0) - params.get(1);
                            }
                        })
                    .buildOperator()
                .buildDefaultGrammar());
        assertEquals(109.0, calc.solveSingleValue("15+hundred-6"), 0);
    }

    @Test
    public void shouldDefineAndUseASimpleGrammarWithContext() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntOperand(new Evaluator
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
                    .evaluator(new ParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildDefaultGrammar());

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
                .addIntOperand(new Evaluator
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
                    .evaluator(new ParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildDefaultGrammar());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        assertEquals(1121.0,
                calc.solveSingleValue(context, "(x = 15 + hundred) + 6 + thousand"), 0);

        // this allows to keep intermediate results
        assertEquals(115, context.get("x"), 0);
    }

    @Test
    public void shouldNotShadowALegitimateConstant() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addIntOperand(new Evaluator
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
                    .evaluator(new ParametricEvaluator
                                <Integer, Map<String,Integer>>() {
                            @Override
                            public Integer evaluate(String value,
                                    List<Integer> params,
                                    Map<String,Integer> context) {
                                return params.get(0) + params.get(1);
                            }
                        })
                    .buildOperator()
                .buildDefaultGrammar());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("hundred", 1_000);

        assertEquals(100.0,
                calc.solveSingleValue(context, "hundred"),
                0);
    }

    @Test
    public void shouldUseDefaultArithmeticCalculatorToSolveSimpleExpression() {
        final ContextCalculator<Double> calculator =
                new ContextCalculator<>(ArithmeticGrammar.INSTANCE);

        assertEquals(1, calculator.solveSingleValue("sin(pi/2)"), 0);
    }

    @Test
    public void shouldReturnResultsForSeparateExpressions() {
        final ContextCalculator<Double> calculator =
                new ContextCalculator<>(ArithmeticGrammar.INSTANCE);

        assertEquals(Arrays.asList(1.0, 7.0, 2.0),
                calculator.solve("sin(pi/2) 5+2 14/7"));
    }

    @Test
    public void shouldPlotTheExpression() {
        final ContextCalculator<Double> calculator =
                new ContextCalculator<>(ArithmeticGrammar.INSTANCE);

        ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("x");

        double accumulator = 0;
        for (double x = 1; x <= 5; x++) {
            final Double y = solution.solveWithVariables("x", x).get(0);
            accumulator += y;
        }

        assertEquals(15, accumulator, 0);
    }

    @Test(expected=EvaluationException.class)
    public void shouldCallEvaluationExceptionIfEvaluationGoesWrong() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addFloatOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            // THIS IS AN ERROR !
                            return Integer.valueOf(value);
                        }
                    })
                .buildDefaultGrammar());

        calc.solveSingleValue("12.34");
    }

}
