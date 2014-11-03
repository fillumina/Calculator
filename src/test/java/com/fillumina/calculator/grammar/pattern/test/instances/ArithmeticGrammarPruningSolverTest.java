package com.fillumina.calculator.grammar.pattern.test.instances;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.PruningSolver;
import com.fillumina.calculator.grammar.pattern.instances.ArithmeticPatternGrammar;
import java.util.Map;
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
                ArithmeticPatternGrammar.INSTANCE, PruningSolver.INSTANCE);
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