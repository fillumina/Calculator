package com.fillumina.utils.interpreter.grammar.pattern.instances;

import java.util.Map;
import com.fillumina.utils.interpreter.Calculator;
import com.fillumina.utils.interpreter.ContextException;
import com.fillumina.utils.interpreter.DefaultSolver;
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
                ArithmeticGrammar.INSTANCE, DefaultSolver.INSTANCE);
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