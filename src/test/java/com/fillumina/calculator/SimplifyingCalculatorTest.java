package com.fillumina.calculator;

import com.fillumina.calculator.instance.ArithmeticGrammar;
import com.fillumina.calculator.util.Mapper;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingCalculatorTest {

    private SimplifyingCalculator<Double, Map<String,Double>> calc =
            new SimplifyingCalculator<>(ArithmeticGrammar.INSTANCE);

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
