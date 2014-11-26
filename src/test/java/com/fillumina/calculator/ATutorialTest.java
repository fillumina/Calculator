package com.fillumina.calculator;

import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.GrammarBuilder;
import com.fillumina.calculator.grammar.ParametricEvaluator;
import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * This is a tutorial to help understand how to use this API.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ATutorialTest {

    /**
     * Let's see how easy is to use the calculator with one of the
     * provided grammars.
     * The results of an expression is usually a {@link List} because it can
     * be comprised of different un-related sub expression: for example
     * the expression {@code "5 + 3 2"} is composed of two separate arithmetic
     * expression and the solution will be the list {@code [8, 2]}.
     * If are interested in the first solution only there is a shortcut to
     * using the {@link List#get(int)} method which is
     * {@link Calculator#solveSingleValue(java.lang.String)}.
     */
    @Test
    public void shouldCalculateASimpleExpression() {
        // Let's create a simple arithmetic calculator
        final Calculator<Double,Map<String,Double>> calculator =
                new Calculator<>(DoubleArithmeticGrammar.INSTANCE);

        // it's easy to make calculations and get results
        assertEquals(12.5, calculator.solve("(20 + 5)/2 ").get(0), 0);

        // to avoid the get(0) there is a specialized solve method
        assertEquals(12.5, calculator.solveSingleValue("(20 + 5)/2 "), 0);
    }

    /**
     * The provided {@link DoubleArithmeticGrammar} has a component that manages
     * a string mapped context that can be used to store and retrieve variables.
     * If an expression contains a variable which is not defined in the
     * context the usual behavior of {@link Calculator#solve(java.lang.String)}
     * methods is to throw a {@link ContextException}. If you need to know
     * which variables are present you can use the method
     * {@link Calculator#createSolutionTree(java.lang.String) } that
     * returns a {@link SolutionTree} which
     * has an helper method to enquire for undefined variables
     * {@link SolutionTree#getUndefinedVariables()}.
     */
    @Test
    public void shouldRequireAVariable() {
        // Let's create a simple arithmetic calculator
        final Calculator<Double,Map<String,Double>> calculator =
                new Calculator<>(DoubleArithmeticGrammar.INSTANCE);

        // we can get a solution tree which can then be queried
        SolutionTree<Double,Map<String,Double>> solutionTree =
                calculator.createSolutionTree("2 * x / 5");

        assertFalse(solutionTree.isSolved());

        // it's possible to ask for undefined variables
        assertEquals("x", solutionTree.getUndefinedVariables().get(0));
    }

    /**
     * The strength of this API is not so much into the provided grammars
     * but in the ability to easily create and use your own. A grammar
     * is an {@link Iterable} over {@link GrammarElement}s but there is an
     * class called {@link com.fillumina.calculator.grammar.Grammar} to help
     * creating a new one. It's probably even better to use one of the provided
     * builders ({@link com.fillumina.calculator.grammar.GrammarBuilder}).
     * Grammars can return whatever type it's needed and are very easy to built.
     * Let's define a simple grammar and use it for a calculation.
     */
    @Test
    public void shouldDefineAndUseASimpleGrammar() {
        // defines a calculator using a custom grammar
        final Calculator<Integer,Void> calc = new Calculator<>(
            // let's create the grammar using a builder
            // the grammar works over integers and doesn't manage a
            // context (Void).
            GrammarBuilder.<Integer,Void>create()
                // this grammar will produce and operates over integer values
                .addIntegerOperand(new Evaluator<Integer, Void>() {
                        @Override
                        public Integer evaluate(String value, Void context) {
                            return Integer.valueOf(value);
                        }
                    })

                // let's add a constant
                .addConstant("hundred", 100)

                // the operator +
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

        assertEquals(109, calc.solveSingleValue("15+hundred-6"), 0);
    }

    /**
     * Let's create a more complex grammar using a string mapped context.
     */
    @Test
    public void shouldDefineAndUseASimpleGrammarWithContext() {
        // creates a calcualtor based on a custom gramamr
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            // creates a grammar over integers with a string mapped context
            new ContextedGrammarBuilder<Integer>()
                // this defines the base operand (integer)
                .addIntegerOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })

                // let's add a constant
                .addConstant("hundred", 100)

                // and the + operator
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

        // we are now ready to do a calculation of an expression with
        // parentheses and whitespaces
        assertEquals(1121,
                calc.solveSingleValue(context, "15 + (hundred + 6) + thousand"),
                0);
    }

    /**
     * A variable can be implicitly defined in the expression and retrieved
     * from the context.
     */
    @Test
    public void shouldDefineAndUseASimpleGrammarWithContextAndSetAVariable() {
        // creates a calculator with a custom grammar
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            // creates a custom grammar with a string mapped context over integer
            new ContextedGrammarBuilder<Integer>()
                // creates the operand
                .addIntegerOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })

                // defines a constant
                .addConstant("hundred", 100)

                // defines the + operator
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

                // craates a grammar with settable variables
                .buildDefaultGrammarWithSettableVariables());

        // defining a context and putting a variable in it
        final Map<String,Integer> context = new HashMap<>();
        context.put("thousand", 1_000);

        // we can (implicitly) set a variable and get it after the calculation
        // from the context
        assertEquals(1121,
                calc.solveSingleValue(context,
                        "(x = 15 + hundred) + 6 + thousand"), 0);

        // this allows to keep intermediate results
        assertEquals(115, context.get("x"), 0);
    }

    /**
     * Of course variables cannot shadow constants (they have higher priority).
     */
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

        // hundred still value 100
        assertEquals(100,
                calc.solveSingleValue(context, "hundred"),
                0);
    }

    /**
     * The solution tree can be a multi-headed tree. The calculator
     * can return different values each a solution of a different sub expression.
     */
    @Test
    public void shouldReturnResultsForSeparateExpressions() {
        final MappedContextSimplifyingCalculator<Double> calc =
                new MappedContextSimplifyingCalculator<>(
                        DoubleArithmeticGrammar.INSTANCE);

        assertEquals(Arrays.asList(1.0, 7.0, 2.0),
                calc.solve("sin(PI/2) 5+2 14/7"));
    }

    /**
     * Using the {@link SolutionTree} it is possible to do a plot using
     * a partially solved tree (with simplify) and then solving it in the loop.
     */
    @Test
    public void shouldPlotTheExpression() {
        // creating a calculator with the default arithmetic grammar
        final MappedContextSimplifyingCalculator<Double> calc =
                new MappedContextSimplifyingCalculator<>(
                        DoubleArithmeticGrammar.INSTANCE);

        // create a solution from an expression
        MappedContextSimplifyingSolutionTree<Double> solution =
                calc.createSolutionTree("x + y");

        // simplify a solution giving to it a value for one of the two variables
        Map<String,Double> context = Mapper.<Double>create("y", 3d);
        solution.simplify(context); // solve the solution substituting 3 to y

        // solve the other variable in a loop (this solution doesn't modify
        // the solution tree).
        double accumulator = 0;
        for (double x = 1; x <= 5; x++) {
            // solve the solution substituting different values of x to it
            final Double y = solution.solveWithVariables("x", x).get(0);
            accumulator += y;
        }

        // (1+3)+(2+3)+(3+3)+(4+3)+(5+3)=(1+2+3+4+5)+(3*5)=15+15
        assertEquals(15 + 15, accumulator, 0);
    }
}
