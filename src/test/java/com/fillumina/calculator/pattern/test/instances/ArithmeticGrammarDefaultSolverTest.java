package com.fillumina.calculator.pattern.test.instances;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.ContextException;
import com.fillumina.calculator.DefaultSolver;
import com.fillumina.calculator.pattern.instances.ArithmeticPatternGrammar;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArithmeticGrammarDefaultSolverTest
        extends ArithmeticGrammarTestBase {

    @Override
    @SuppressWarnings("unchecked")
    public Calculator<Double, Map<String, Double>> getCalculator() {
        return new Calculator<>(
                ArithmeticPatternGrammar.INSTANCE, DefaultSolver.INSTANCE);
    }

    @Test(expected=ContextException.class)
    public void shouldDetectAnInexistentFunction() {
        calculate("-3 * sinto(pi/2 -(8 + 2))");
    }

    @Test(expected=ContextException.class)
    public void shouldDetectAnInexistentSymbol() {
        calculate("-3 @ sin(pi/2 -(8 + 2))");
    }
}