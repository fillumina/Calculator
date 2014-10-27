package com.fillumina.calculator.util;

import com.fillumina.calculator.DefaultInterpreter;
import com.fillumina.calculator.Interpreter;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.grammar.pattern.instances.ArithmeticGrammar;
import java.util.Collection;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreePrinterTest {

    private Interpreter<Double,Map<String, Double>> interpreter;

    @Before
    public void init() {
        final ArithmeticGrammar grammar = ArithmeticGrammar.INSTANCE;
        interpreter = new DefaultInterpreter<>(grammar);
    }

    @Test
    public void shouldPrintACorrectTree() {
        final Collection<Node<Double, Map<String,Double>>> collection =
                interpreter.buildSolutionTree("sin(2 + 1/2)");
        final String prettyPrint = TreePrinter.prettyPrintFull(collection);
        assertEquals("sin OPERATOR\n" +
                        " + OPERATOR\n" +
                        "  2 OPERAND\n" +
                        "  / OPERATOR\n" +
                        "   1 OPERAND\n" +
                        "   2 OPERAND\n",
                prettyPrint);
    }

    @Test
    public void shouldPrintATwoRootsTree() {
        final Collection<Node<Double, Map<String,Double>>> collection =
                interpreter.buildSolutionTree("sin(2 + 1/2) - 8");
        final String prettyPrint = TreePrinter.prettyPrintFull(collection);
        assertEquals("- OPERATOR\n" +
                        " sin OPERATOR\n" +
                        "  + OPERATOR\n" +
                        "   2 OPERAND\n" +
                        "   / OPERATOR\n" +
                        "    1 OPERAND\n" +
                        "    2 OPERAND\n" +
                        " 8 OPERAND\n",
                prettyPrint);
    }

}
