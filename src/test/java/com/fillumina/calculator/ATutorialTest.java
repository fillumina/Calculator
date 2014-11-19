package com.fillumina.calculator;

import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.GrammarBuilder;
import com.fillumina.calculator.grammar.ParametricEvaluator;
import com.fillumina.calculator.grammar.instance.ArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * This is a tutorial to help understand how to use this API.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ATutorialTest {


    @Test
    public void shouldCalculateASimpleExpression() {
        // Let's create a simple arithmetic calculator
        final Calculator<Double,Map<String,Double>> calculator =
                new Calculator<>(ArithmeticGrammar.INSTANCE);

        // it's easy to make calculations and get results
        assertEquals(12.5, calculator.solveSingleValue("(20 + 5)/2 "), 0);
    }

    @Test
    public void shouldRequireAVariable() {
        // Let's create a simple arithmetic calculator
        final Calculator<Double,Map<String,Double>> calculator =
                new Calculator<>(ArithmeticGrammar.INSTANCE);

        // we can get a solution tree which can then be queried
        SolutionTree<Double,Map<String,Double>> solutionTree =
                calculator.createSolutionTree("2 * x / 5");

        // it's possible to ask for undefined variables
        assertEquals("x", solutionTree.getUndefinedVariables().get(0));
    }

    /**
     * Let's define a simple grammar and use it for a calculation
     */
    @Test
    public void shouldDefineAndUseASimpleGrammar() {
        final Calculator<Integer,Void> calc = new Calculator<>(
            GrammarBuilder.<Integer,Void>create()
                // this grammar will read integer values
                .addIntegerOperand(new Evaluator<Integer, Void>() {
                        @Override
                        public Integer evaluate(String value, Void context) {
                            return Integer.valueOf(value);
                        }
                    })

                // let's add a constant
                .addConstant("hundred", 100)

                // and the operator +
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

                // and the operator -
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
                // this basic builder doesn't include whitespaces management
                .buildGrammar());

        assertEquals(109.0, calc.solveSingleValue("15+hundred-6"), 0);
    }

    /**
     * Let's create a more complex grammar using a mapped context
     */
    @Test
    public void shouldDefineAndUseASimpleGrammarWithContext() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntegerOperand(new Evaluator
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
                // this builder allows for whitespaces and variables
                .buildDefaultGrammarWithVariables());

        // defining a context with a variable in it
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        // we are now ready to do a calculation
        assertEquals(1121.0,
                calc.solveSingleValue(context, "15 + (hundred + 6) + thousand"), 0);
    }

    @Test
    public void shouldDefineAndUseASimpleGrammarWithContextAndSetAVariable() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntegerOperand(new Evaluator
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
                // let's craate a grammar with settable variables now
                .buildDefaultGrammarWithSettableVariables());

        // defining a context
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        // we can set a variable and get it after the calculation from the context
        assertEquals(1121.0,
                calc.solveSingleValue(context,
                        "(x = 15 + hundred) + 6 + thousand"), 0);

        // this allows to keep intermediate results
        assertEquals(115, context.get("x"), 0);
    }

    /** Of course variables cannot shadow constants. */
    @Test
    public void shouldNotShadowALegitimateConstant() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntegerOperand(new Evaluator
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
                .buildGrammar());

        // trying to redefine a constant with a different value
        final Map<String,Integer> context = new HashMap<>();
        context.put("hundred", 1_000);

        assertEquals(100.0,
                calc.solveSingleValue(context, "hundred"),
                0);
    }

    /**
     * The solver understands many heads for the solution tree and so
     * it can return different values.
     */
    @Test
    public void shouldReturnResultsForSeparateExpressions() {
        final MappedContextSimplifyingCalculator<Double> calc =
                new MappedContextSimplifyingCalculator<>(ArithmeticGrammar.INSTANCE);

        assertEquals(Arrays.asList(1.0, 7.0, 2.0),
                calc.solve("sin(PI/2) 5+2 14/7"));
    }

    /**
     * Using the {@link SolutionTree} it is possible to solve
     */
    @Test
    public void shouldPlotTheExpression() {
        final MappedContextSimplifyingCalculator<Double> calc =
                new MappedContextSimplifyingCalculator<>(ArithmeticGrammar.INSTANCE);

        MappedContextSimplifyingSolutionTree<Double> solution =
                calc.createSolutionTree("x + y");

        Map<String,Double> context = Mapper.<Double>create("y", 3d);
        solution.simplify(context); // solve the solution substituting 3 to y

        double accumulator = 0;
        for (double x = 1; x <= 5; x++) {
            // solve the solution substituting different values of x to it
            final Double y = solution.solveWithVariables("x", x).get(0);
            accumulator += y;
        }

        assertEquals(15 + 15, accumulator, 0);
    }
}
