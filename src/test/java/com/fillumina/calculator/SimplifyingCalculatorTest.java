package com.fillumina.calculator;

import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingCalculatorTest {

    private SimplifyingCalculator<Double, Map<String,Double>> calc =
            new SimplifyingCalculator<>(DoubleArithmeticGrammar.INSTANCE);

    @Test
    public void shouldBeAbleToSimplifyAnExpression() {
        SimplifyingSolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("x + y + 3");

        assertFalse(solution.isSolved());

        solution.simplify(Mapper.<Double>create("x", 2d));

        assertFalse(solution.isSolved());

        solution.simplify(Mapper.<Double>create("y", 3d));

        assertTrue(solution.isSolved());
    }
}
