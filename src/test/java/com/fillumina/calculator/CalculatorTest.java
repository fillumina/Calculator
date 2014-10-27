package com.fillumina.calculator;

import com.fillumina.calculator.grammar.fast.instances.FastArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CalculatorTest {

    private Calculator<Double,Map<String,Double>> calculator =
            Calculator.createPruning(FastArithmeticGrammar.INSTANCE);

    @Test
    public void shouldCalculateASimpleExpression() {
        assertEquals(12.5, calculator.solveSingleValue("(20 + 5)/2 "), 0.001);
    }

    @Test
    public void shouldRequireAVariable() {
        SolutionTree<Double,Map<String,Double>> solutionTree =
                calculator.createSolutionTree("2 * x / 5");
        solutionTree.solve();
        assertFalse(solutionTree.isSolved());

        solutionTree.solve(Mapper.<Double>create("x", 10.0d));

        assertEquals(4.0, (double)solutionTree.getSingleSolution(), 0.0001);
    }
}
