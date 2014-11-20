package com.fillumina.calculator;

import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ContextSolutionTreeTest {

    private MappedContextSimplifyingCalculator<Double> calculator =
            new MappedContextSimplifyingCalculator<>(DoubleArithmeticGrammar.INSTANCE);

    @Test
    public void shouldHaveAStaticSolution() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");
        solution.simplify();
        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldRecognizeTheVariable() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");

        solution.simplify();

        assertTrue(solution.getUndefinedVariables().contains("x"));
    }

    @Test
    public void shouldSolveUsingTheVariable() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2");

        solution.simplify();
        List<Double> list = solution.solveWithVariables("x", 10d);
        assertEquals(12d, list.get(0), 0);
    }

    @Test
    public void shoudPartiallySolve() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplify();
        List<Double> list = solution.simplifyWithVariables("x", 10);
        assertNull(list);
        assertFalse(solution.isSolved());
    }

    @Test
    public void shoudSolveForTwoVariablesAndClone() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final SolutionTree<Double, Map<String,Double>> clonedSolution =
                solution.clone();

        assertEquals(17, solution.simplifyWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudCloneThePartialSolution() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final MappedContextSimplifyingSolutionTree<Double> clonedSolution = solution.clone();

        assertEquals(17, clonedSolution.simplifyWithVariables("y", 5d).get(0), 0);
    }

    @Test
    public void shoudTheOriginalSolutionOfAClonedSolvedOneBeStillUnsolved() {
        final MappedContextSimplifyingSolutionTree<Double> solution =
                calculator.createSolutionTree("y + x + 2");

        solution.simplifyWithVariables("x", 10d);

        final MappedContextSimplifyingSolutionTree<Double> clonedSolution = solution.clone();

        clonedSolution.simplifyWithVariables("y", 5d);

        assertFalse(solution.isSolved());
    }
}
