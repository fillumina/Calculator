package com.fillumina.calculator.grammar.pattern.instances;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.ContextException;
import com.fillumina.calculator.FastSolver;
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
                ArithmeticGrammar.INSTANCE, FastSolver.INSTANCE);
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