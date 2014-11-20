package com.fillumina.calculator;

import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Arrays;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionTreeTest {

    private Calculator<Double,Map<String,Double>> calc =
            new Calculator(DoubleArithmeticGrammar.INSTANCE);

    @Test
    public void shouldCreateASolutionTree() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + (8 / 2)");

        assertNotNull(solutionTree);
    }

    @Test
    public void shouldSolveASolutionTree() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + (8 / 2)");

        solutionTree.solve();

        assertTrue(solutionTree.isSolved());
    }
    @Test
    public void shouldGetTheSolition() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + (8 / 2)");

        solutionTree.solve();

        assertEquals(6, solutionTree.getSingleSolution(), 0);
    }

    @Test
    public void shouldGetAMultipleSolution() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + (8 / 2) 17");

        solutionTree.solve();

        assertEquals(Arrays.asList(6.0, 17.0), solutionTree.getSolution());
    }

    @Test(expected=ContextException.class)
    public void shouldNotSolveASolutionTreeWithAVariable() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + x");

        solutionTree.solve();
    }

    @Test
    public void shouldSolveASolutionTreeGivenTheVariable() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + x");

        final Map<String,Double> context = Mapper.<Double>create("x", 4.0);

        solutionTree.solve(context);

        assertTrue(solutionTree.isSolved());
    }

    @Test
    public void shouldGetASolutionOfASolutionTreeGivenTheVariable() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + x");

        final Map<String,Double> context = Mapper.<Double>create("x", 4.0);

        solutionTree.solve(context);

        assertEquals(6.0, solutionTree.getSingleSolution(), 0);
    }

    @Test
    public void shouldCloneASolutionTreeWithoutSolvingTheVariable() {
        final SolutionTree<Double,Map<String,Double>> solutionTree =
                calc.createSolutionTree("2 + x");

        final SolutionTree<Double,Map<String,Double>> clone =
                solutionTree.clone();

        final Map<String,Double> context = Mapper.<Double>create("x", 4.0);
        solutionTree.solve(context);

        assertFalse(clone.isSolved());
    }

}
