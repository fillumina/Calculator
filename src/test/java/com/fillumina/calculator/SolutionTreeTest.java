package com.fillumina.calculator;

import com.fillumina.calculator.instance.ArithmeticGrammar;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionTreeTest {

    private ContextCalculator<Double> calculator =
            new ContextCalculator<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");
        solution.simplify();
        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldRecognizeTheVariable() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");

        solution.simplify();

        assertTrue(solution.getUndefinedVariables().contains("x"));
    }

    @Test
    public void shouldSolveUsingTheVariable() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");

        solution.simplify();
        List<Double> list = solution.solveWithVariables("x", 10d);
        assertEquals(12d, list.get(0), 0);
    }

    @Test
    public void shoudPartiallySolve() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplify();
        List<Double> list = solution.simplifyWithVariables("x", 10);
        assertNull(list);
        assertFalse(solution.isSolved());
    }

    @Test
    public void shoudSolveForTwoVariablesAndClone() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final SolutionTree<Double, Map<String,Double>> clonedSolution =
                solution.clone();

        assertEquals(17, solution.simplifyWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudCloneThePartialSolution() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final ContextSolutionTree<Double> clonedSolution = solution.clone();

        assertEquals(17, clonedSolution.simplifyWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudTheOriginalSolutionOfAClonedSolvedOneBeStillUnsolved() {
        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final ContextSolutionTree<Double> clonedSolution = solution.clone();

        clonedSolution.simplifyWithVariables("y", 5d);

        assertFalse(solution.isSolved());
    }
}
