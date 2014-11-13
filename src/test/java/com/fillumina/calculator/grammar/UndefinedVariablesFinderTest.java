package com.fillumina.calculator.grammar;

import com.fillumina.calculator.ContextCalculator;
import com.fillumina.calculator.ContextSolutionTree;
import com.fillumina.calculator.Interpreter;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.UndefinedVariablesFinder;
import com.fillumina.calculator.instance.ArithmeticGrammar;
import com.fillumina.calculator.interpreter.DefaultInterpreter;
import com.fillumina.calculator.util.Mapper;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UndefinedVariablesFinderTest {

    private final Interpreter<Double, Map<String,Double>> interpreter =
            new DefaultInterpreter<>(ArithmeticGrammar.INSTANCE);

    @Test
    public void shouldRecognizeTheUndefinedVariables() {
        final List<Node<Double, Map<String,Double>>> nodes =
                interpreter.buildSolutionTree("x + 2 * y + sin(2 * PI)");
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
                interpreter.buildSolutionTree("2 + sin(2 * PI)");
        final List<String> vars =
                UndefinedVariablesFinder.INSTANCE.find(nodes);
        assertTrue(vars.isEmpty());
    }

    @Test
    public void shouldRecognizeOnlyTheUndefinedVariable() {
        final ContextCalculator<Double> calculator =
            new ContextCalculator<>(ArithmeticGrammar.INSTANCE);

        final ContextSolutionTree<Double> solution =
                calculator.createSolutionTree("x + 2 * y + sin(2 * PI)");

        final Map<String,Double> context = Mapper.create("x", 1.2d);
        solution.simplify(context);

        final List<String> undefinedVars = solution.getUndefinedVariables();
        assertEquals(1, undefinedVars.size());
        assertTrue(undefinedVars.contains("y"));
    }
}
