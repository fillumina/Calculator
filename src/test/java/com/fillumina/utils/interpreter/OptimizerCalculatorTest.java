package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import com.fillumina.utils.interpreter.util.Mapper;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OptimizerCalculatorTest {

    @SuppressWarnings("unchecked")
    private static final Map<String, Double> EMPTY_MAP =
            (Map<String,Double>)Collections.EMPTY_MAP;

    private OptimizerCalculator<Double, Map<String,Double>> calculator =
            new OptimizerCalculator<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final SolutionOptimizer<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.getSingleValueSolution(), 0.01);
    }

    @Test
    public void shouldSolveAStaticSolutionAnyway() {
        final SolutionOptimizer<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.solve(EMPTY_MAP).get(0), 0.01);
    }

    @Test
    public void shouldNotHaveAStaticSolution() {
        final SolutionOptimizer<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldCalculateTheSolutionOnceAVariableIsInserted() {
        final SolutionOptimizer<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        final Map<String,Double> context = Mapper.create("x", 3.0/4.0);
        assertEquals(0.8414709848078965, solution.solve(context).get(0), 0.001);
    }

    @Test
    public void shouldCalculateTheSolutionOnceASecondVariableIsInserted() {
        final SolutionOptimizer<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4) + y");

        final Map<String,Double> context1 = Mapper.create("x", 3.0/4.0);
        solution.solve(context1);
        assertFalse(solution.isSolved());

        final Map<String,Double> context2 = Mapper.create("y", 1.0);
        assertEquals(1.8414709848078965, solution.solve(context2).get(0), 0.001);
    }
}
