package com.fillumina.utils.interpreter.util;

import com.fillumina.utils.interpreter.GrammarBasedInterpreter;
import com.fillumina.utils.interpreter.Interpreter;
import com.fillumina.utils.interpreter.Node;
import java.util.Collection;
import com.fillumina.utils.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreePrinterTest {

    private Interpreter<Double,Map<String, Double>> interpreter;

    @Before
    public void init() {
        final ArithmeticGrammar grammar = ArithmeticGrammar.INSTANCE;
        interpreter = new GrammarBasedInterpreter<>(grammar);
    }

    @Test
    public void shouldPrintACorrectTree() {
        final Collection<Node<Double, Map<String,Double>>> collection =
                interpreter.parse("sin(2 + 1/2)");
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
                interpreter.parse("sin(2 + 1/2) - 8");
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
