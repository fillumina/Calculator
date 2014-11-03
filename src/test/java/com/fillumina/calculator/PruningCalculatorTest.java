package com.fillumina.calculator;

import com.fillumina.calculator.grammar.pattern.instances.ArithmeticPatternGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Collections;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PruningCalculatorTest {

    @SuppressWarnings("unchecked")
    private static final Map<String, Double> EMPTY_MAP =
            (Map<String,Double>)Collections.EMPTY_MAP;

    private Calculator<Double, Map<String,Double>> calculator =
            Calculator.createPruning(ArithmeticPatternGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");
        solution.solve();
        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.getSingleSolution(), 0.01);
    }

    @Test
    public void shouldSolveAStaticSolutionAnyway() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("(1 / 4)");
        solution.solve();
        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.solve(EMPTY_MAP).get(0), 0.01);
    }

    @Test
    public void shouldNotHaveAStaticSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldCalculateTheSolutionOnceAVariableIsInserted() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4)");

        final Map<String,Double> context = Mapper.create("x", 3.0/4.0);
        assertEquals(0.8414709848078965, solution.solve(context).get(0), 0.001);
    }

    @Test
    public void shouldCalculateTheSolutionOnceASecondVariableIsInserted() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("sin(x + 1 / 4) + y");

        final Map<String,Double> context1 = Mapper.create("x", 3.0/4.0);
        solution.solve(context1);
        assertFalse(solution.isSolved());

        final Map<String,Double> context2 = Mapper.create("y", 1.0);
        assertEquals(1.8414709848078965, solution.solve(context2).get(0), 0.001);
    }
}
