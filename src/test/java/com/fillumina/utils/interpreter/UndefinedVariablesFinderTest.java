package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import com.fillumina.utils.interpreter.util.Mapper;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UndefinedVariablesFinderTest {

    private final Interpreter<Double, Map<String,Double>> interpreter =
            new Interpreter<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldRecognizeTheUndefinedVariables() {
        final List<Node<Double, Map<String,Double>>> nodes =
                interpreter.parse("x + 2 * y + sin(2 * pi)");
        final List<String> vars =
                UndefinedVariablesFinder.INSTANCE.find(nodes);
        assertEquals(2, vars.size());
        assertTrue(vars.contains("x"));
        assertTrue(vars.contains("y"));
        assertFalse(vars.contains("sin"));
    }

    @Test
    public void shouldReturnAnEmptyListIfNoVariableIsUndefined() {
        final List<Node<Double, Map<String,Double>>> nodes =
                interpreter.parse("2 + sin(2 * pi)");
        final List<String> vars =
                UndefinedVariablesFinder.INSTANCE.find(nodes);
        assertTrue(vars.isEmpty());
    }

    @Test
    public void shouldRecognizeOnlyTheUndefinedVariable() {
        final OptimizerCalculator<Double, Map<String,Double>> calculator =
            new OptimizerCalculator<>(ArithmeticGrammar.INSTANCE);

        final Map<String,Double> context = Mapper.create("x", 1.2d);
        final SolutionOptimizer<Double,Map<String,Double>> solution =
                calculator.createSolutionTree("x + 2 * y + sin(2 * pi)", context);

        final List<String> undefinedVars = solution.getUndefinedVariables();
        assertEquals(1, undefinedVars.size());
        assertTrue(undefinedVars.contains("y"));
    }
}
