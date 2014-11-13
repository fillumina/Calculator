package com.fillumina.calculator;

import com.fillumina.calculator.element.CloseParentheses;
import com.fillumina.calculator.element.OpenParentheses;
import com.fillumina.calculator.element.VariableContextManager;
import com.fillumina.calculator.element.WhiteSpace;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.interpreter.DefaultInterpreter;
import com.fillumina.calculator.util.TreePrinter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingSolverTest {

    @Test
    public void shouldPruneTheTree() {
        final GrammarElement<String,Map<String,String>> operator =
                new TestContextOperator("@", 0, 0, 100);

        final GrammarElement<String,Map<String,String>> number =
                new TestContextOperand("\\d+", 0);

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> variable =
                VariableContextManager.instance();

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> openPar =
                (GrammarElement<String,Map<String,String>>)
                OpenParentheses.<String,Map<String,String>>round();

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> closePar =
                (GrammarElement<String,Map<String,String>>)
                CloseParentheses.<String,Map<String,String>>round();

        final WhiteSpace<String, Map<String, String>> whitespace =
                new WhiteSpace<>(0, " ");

        @SuppressWarnings("unchecked")
        final Grammar<String,Map<String,String>> grammar =
                new Grammar<>(operator, number, variable, openPar, closePar,
                        whitespace);

        final Interpreter<String,Map<String,String>> interpreter =
                new DefaultInterpreter<>(grammar);

        final List<Node<String,Map<String,String>>> solution =
                interpreter.buildSolutionTree("@ 0 a (@ 1 2) 3");

        final Map<String,String> context = new HashMap<>();

        final String treePrint = TreePrinter.prettyPrintFull(solution);
        System.out.println("treeprint\n" + treePrint);
        assertEquals("@ OPERATOR\n" +
                     " 0 OPERAND\n" +
                     " a UNRECOGNIZED\n" +
                     " @ OPERATOR\n" +
                     "  1 OPERAND\n" +
                     "  2 OPERAND\n" +
                     " 3 OPERAND\n", treePrint);

        final List<String> result =
                SimplifyingSolver.INSTANCE.solve(solution, context);

        assertNull(result);

        final String prunedTreePrint = TreePrinter.prettyPrintFull(solution);

        System.out.println("result: " + result);
        System.out.println("pruned:\n" + prunedTreePrint);

        assertEquals(
                "@ OPERATOR\n" +
                " 0 OPERAND -> 0\n" +
                " a UNRECOGNIZED\n" +
                " @ OPERATOR -> @[1, 2]\n" +
                " 3 OPERAND -> 3\n",
                prunedTreePrint
        );
    }
}
