package com.fillumina.utils.interpreter.grammar.pattern.instances;

import java.util.Map;
import com.fillumina.utils.interpreter.Calculator;
import com.fillumina.utils.interpreter.DefaultSolver;

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
                ArithmeticGrammar.INSTANCE, DefaultSolver.INSTANCE);
    }

}