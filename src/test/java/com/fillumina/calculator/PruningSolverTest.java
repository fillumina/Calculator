package com.fillumina.calculator;

import com.fillumina.calculator.element.VariableContextManager;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.interpreter.DefaultInterpreter;
import com.fillumina.calculator.pattern.PatternCloseParentheses;
import com.fillumina.calculator.pattern.PatternOpenParentheses;
import com.fillumina.calculator.pattern.PatternWhiteSpace;
import com.fillumina.calculator.pattern.test.TestContextOperand;
import com.fillumina.calculator.pattern.test.TestContextOperator;
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
public class PruningSolverTest {

    @Test
    public void shouldPruneTheTree() {
        final GrammarElement<String,Map<String,String>> operator =
                new TestContextOperator("\\@", 0, 0, 100);

        final GrammarElement<String,Map<String,String>> number =
                new TestContextOperand("\\d+", 0);

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> variable =
                VariableContextManager.instance();

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> openPar =
                (GrammarElement<String,Map<String,String>>)
                PatternOpenParentheses.ROUND;

        @SuppressWarnings("unchecked")
        final GrammarElement<String,Map<String,String>> closePar =
                (GrammarElement<String,Map<String,String>>)
                PatternCloseParentheses.ROUND;

        @SuppressWarnings("unchecked")
        final PatternWhiteSpace<String, Map<String, String>> whitespace =
                (PatternWhiteSpace<String,Map<String,String>>)
                PatternWhiteSpace.INSTANCE;

        @SuppressWarnings("unchecked")
        final Grammar<String,Map<String,String>> grammar =
                new Grammar<>(operator, number, variable, openPar, closePar,
                        whitespace);

        final Interpreter<String,Map<String,String>> interpreter =
                new DefaultInterpreter<>(grammar);

        final List<Node<String,Map<String,String>>> solution =
                interpreter.buildSolutionTree("@ 1 a (@ 1 2) 3");

        final Map<String,String> context = new HashMap<>();

        final String treePrint = TreePrinter.prettyPrintFull(solution);
//        System.out.println("treeprint\n" + treePrint);
        assertEquals("@ OPERATOR\n" +
                     " 1 OPERAND\n" +
                     " a UNRECOGNIZED\n" +
                     " @ OPERATOR\n" +
                     "  1 OPERAND\n" +
                     "  2 OPERAND\n" +
                     " 3 OPERAND\n", treePrint);

        final List<String> result =
                SimplifyingSolver.INSTANCE.solve(solution, context);

        assertNull(result);

        final String prunedTreePrint = TreePrinter.prettyPrintFull(solution);

//        System.out.println("result: " + result);
//        System.out.println("pruned:\n" + prunedTreePrint);

        assertEquals(
                "@ OPERATOR\n" +
                " 1 OPERAND -> 1\n" +
                " a UNRECOGNIZED\n" +
                " @ OPERATOR -> {@[1, 2]}\n" +
                " 3 OPERAND -> 3\n",
                prunedTreePrint
        );
    }
}
