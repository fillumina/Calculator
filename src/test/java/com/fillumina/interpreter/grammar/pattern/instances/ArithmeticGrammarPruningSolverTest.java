package com.fillumina.interpreter.grammar.pattern.instances;

import com.fillumina.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import java.util.Map;
import com.fillumina.interpreter.Calculator;
import com.fillumina.interpreter.PruningSolver;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArithmeticGrammarPruningSolverTest
        extends ArithmeticGrammarTestBase {


    @Override
    @SuppressWarnings("unchecked")
    public Calculator<Double, Map<String, Double>> getCalculator() {
        return new Calculator<>(
                ArithmeticGrammar.INSTANCE, PruningSolver.INSTANCE);
    }

    @Test(expected=NullPointerException.class)
    public void shouldReturnNullIfThereIsAnInexistentFunction() {
        calculate("-3 * sinto(pi/2 -(8 + 2))");
    }

    @Test(expected=NullPointerException.class)
    public void shouldReturnNullIfThereIsAnInexistentSymbol() {
        calculate("-3 @ sin(pi/2 -(8 + 2))");
    }
}