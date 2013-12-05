package com.fillumina.utils.interpreter.util;

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
        interpreter = new Interpreter<>(grammar);
    }

    @Test
    public void shouldPrintACorrectTree() {
        final Collection<Node<Double, Map<String,Double>>> collection =
                interpreter.parse("sin(2 + 1/2)");
        final String prettyPrint = TreePrinter.prettyPrint(collection);
        assertEquals("sin\n +\n  2\n  /\n   1\n   2\n", prettyPrint);
    }

    @Test
    public void shouldPrintATwoRootsTree() {
        final Collection<Node<Double, Map<String,Double>>> collection =
                interpreter.parse("sin(2 + 1/2) 1 - 8");
        final String prettyPrint = TreePrinter.prettyPrint(collection);
        assertEquals("sin\n +\n  2\n  /\n   1\n   2\n-\n 1\n 8\n", prettyPrint);
    }

}