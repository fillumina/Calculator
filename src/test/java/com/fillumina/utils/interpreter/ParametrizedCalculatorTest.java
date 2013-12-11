package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import com.fillumina.utils.interpreter.util.Mapper;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParametrizedCalculatorTest {

    @SuppressWarnings("unchecked")
    private static final Map<String, Double> EMPTY_MAP =
            (Map<String,Double>)Collections.EMPTY_MAP;

    private ParametrizedCalculator<Double, Map<String,Double>> calculator =
            new ParametrizedCalculator<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final OptimizedSolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(solution.hasStaticSolution());
        assertEquals(0.25d, solution.getSingleValueSolution(), 0.01);
    }

    @Test
    public void shouldSolveAStaticSolutionAnyway() {
        final OptimizedSolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(solution.hasStaticSolution());
        assertEquals(0.25d, solution.solve(EMPTY_MAP).get(0), 0.01);
    }

    @Test
    public void shouldNotHaveAStaticSolution() {
        final OptimizedSolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        assertFalse(solution.hasStaticSolution());
    }

    @Test
    public void shouldCalculateTheSolutionOnceAVariableIsInserted() {
        OptimizedSolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        final Map<String,Double> context = Mapper.create("x", 3.0/4.0);
        assertEquals(0.8414709848078965, solution.solve(context).get(0), 0.001);
    }
}
