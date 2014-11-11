package com.fillumina.calculator.instance;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.ContextException;
import com.fillumina.calculator.DefaultSolver;
import com.fillumina.calculator.pattern.test.instances.ArithmeticGrammarTestBase;
import com.fillumina.calculator.treebuilder.ParenthesisMismatchedException;
import java.util.Map;
import static org.junit.Assert.assertEquals;
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

    @Test(expected=ParenthesisMismatchedException.class)
    public void shouldDetectAMismatchedParentheses() {
        calculate("sin(x + (1/x)");
    }

    @Test
    public void shouldIgnoresAnEmptyParentheses() {
        calculate("4 + ( ) 5");
    }
}