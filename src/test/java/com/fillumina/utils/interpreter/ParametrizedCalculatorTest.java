package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.ParametrizedCalculator.OptimizedSolutionTree;
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

    private ParametrizedCalculator<Double, Map<String,Double>> calculator =
            new ParametrizedCalculator<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        OptimizedSolutionTree<Double, Map<String,Double>> tree =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(tree.hasStaticSolution());
        assertEquals(0.25d, tree.getSingleValueSolution(), 0.01);
    }

    @Test
    public void shouldSolveAStaticSolutionAnyway() {
        OptimizedSolutionTree<Double, Map<String,Double>> tree =
                calculator.createSolutionTree("(1 / 4)");

        assertTrue(tree.hasStaticSolution());
        assertEquals(0.25d,
                tree.solveSingleValue((Map<String,Double>)Collections.EMPTY_MAP),
                0.01);
    }

    @Test
    public void shouldNotHaveAStaticSolution() {
        OptimizedSolutionTree<Double, Map<String,Double>> tree =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        assertFalse(tree.hasStaticSolution());
    }

    @Test
    public void shouldCalculateTheSolutionOnceAVariableIsInserted() {
        OptimizedSolutionTree<Double, Map<String,Double>> tree =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        final Map<String,Double> context = Mapper.create("x", 3.0/4.0);
        assertEquals(0.8414709848078965, tree.solveSingleValue(context), 0.001);
    }
}
