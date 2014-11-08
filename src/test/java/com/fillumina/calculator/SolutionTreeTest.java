package com.fillumina.calculator;

import com.fillumina.calculator.pattern.instances.ArithmeticPatternGrammar;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionTreeTest {

    private Calculator<Double, Map<String,Double>> calculator =
            Calculator.createPruning(ArithmeticPatternGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("x + 2");
        solution.solve();
        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldRecognizeTheVariable() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("x + 2");

        solution.solve();

        assertTrue(solution.getUndefinedVariables().contains("x"));
    }

    @Test
    public void shouldSolveUsingTheVariable() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("x + 2");

        solution.solve();
        List<Double> list = solution.solveWithVariables("x", 10d);
        assertEquals(12d, list.get(0), 0);
    }

    @Test
    public void shoudPartiallySolve() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.solve();
        List<Double> list = solution.solveWithVariables("x", 10);
        assertNull(list);
        assertFalse(solution.isSolved());
    }

    @Test
    public void shoudSolveForTwoVariablesAndClone() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.solveWithVariables("x", 10d);

        final SolutionTree<Double, Map<String,Double>> clonedSolution =
                solution.clone();

        assertEquals(17, solution.solveWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudCloneThePartialSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.solveWithVariables("x", 10d);

        final SolutionTree<Double, Map<String,Double>> clonedSolution =
                solution.clone();

        assertEquals(17, clonedSolution.solveWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudTheOriginalSolutionOfAClonedSolvedOneBeStillUnsolved() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.solveWithVariables("x", 10d);

        final SolutionTree<Double, Map<String,Double>> clonedSolution =
                solution.clone();

        clonedSolution.solveWithVariables("y", 5d);

        assertFalse(solution.isSolved());
    }
}
