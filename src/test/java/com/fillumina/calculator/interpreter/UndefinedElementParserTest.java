package com.fillumina.calculator.interpreter;

import com.fillumina.calculator.DefaultTokenizer;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.Tokenizer;
import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import com.fillumina.calculator.util.TreePrinter;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UndefinedElementParserTest {

    private final Tokenizer<Double, Map<String, Double>> tokenizer =
            new DefaultTokenizer<>(DoubleArithmeticGrammar.INSTANCE);

    @Test
    public void shouldRecognizeTheUndefinedVariable() {
        final List<Node<Double, Map<String,Double>>> nodes =
                tokenizer.tokenize("boh(2 * pi)");
        WhiteSpaceCleaner.INSTANCE.executeOn(nodes);

        final Node<Double, Map<String,Double>> undefined = nodes.get(0);
        assertEquals("boh", undefined.getExpression());
        assertNull(undefined.getGrammarElement());

        new UndefinedElementParser(DoubleArithmeticGrammar.INSTANCE)
                .executeOn(nodes);

        final Node<Double, Map<String,Double>> unrecognized = nodes.get(0);
        assertEquals("boh", unrecognized.getExpression());
        assertEquals(GrammarElementType.UNRECOGNIZED,
                unrecognized.getGrammarElement().getType());
    }

    @Test
    public void shouldRecognizeAnUndefinedVariableInMiddleNode() {
        final List<Node<Double, Map<String,Double>>> nodes =
                tokenizer.tokenize("sin(2 * boh)");
        WhiteSpaceCleaner.INSTANCE.executeOn(nodes);

        assertEquals("sin OPERATOR\n" +
                    "( OPEN_PAR\n" +
                    "2 OPERAND\n" +
                    "* OPERATOR\n" +
                    "boh \n" +
                    ") CLOSED_PAR\n",
                TreePrinter.prettyPrintFull(nodes));

        new UndefinedElementParser(DoubleArithmeticGrammar.INSTANCE)
                .executeOn(nodes);

        assertEquals("sin OPERATOR\n" +
                    "( OPEN_PAR\n" +
                    "2 OPERAND\n" +
                    "* OPERATOR\n" +
                    "boh UNRECOGNIZED\n" + // <-------[]
                    ") CLOSED_PAR\n",
                TreePrinter.prettyPrintFull(nodes));
    }


    @Test
    public void shouldRecognizeTwoUndefinedVariables() {
        final List<Node<Double, Map<String,Double>>> nodes =
                tokenizer.tokenize("meh(2 * boh)");
        WhiteSpaceCleaner.INSTANCE.executeOn(nodes);

        assertEquals("meh \n" +
                    "( OPEN_PAR\n" +
                    "2 OPERAND\n" +
                    "* OPERATOR\n" +
                    "boh \n" +
                    ") CLOSED_PAR\n",
                TreePrinter.prettyPrintFull(nodes));

        new UndefinedElementParser(DoubleArithmeticGrammar.INSTANCE)
                .executeOn(nodes);

        assertEquals("meh UNRECOGNIZED\n" +  // <-----[]
                    "( OPEN_PAR\n" +
                    "2 OPERAND\n" +
                    "* OPERATOR\n" +
                    "boh UNRECOGNIZED\n" + // <-------[]
                    ") CLOSED_PAR\n",
                TreePrinter.prettyPrintFull(nodes));
    }

}
