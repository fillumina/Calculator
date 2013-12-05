package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.pattern.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.pattern.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.pattern.VariableContextManager;
import com.fillumina.utils.interpreter.util.TreePrinter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PruningSolverTest {

    public static void main(final String[] args) {
        new PruningSolverTest().shouldPruneTheTree();
    }

    @Test
    public void shouldPruneTheTree() {
        final GrammarElement<String,Map<String,String>> operator =
                new TestContextOperator("\\@\\ *", 0, 0, 100);

        final GrammarElement<String,Map<String,String>> number =
                new TestContextOperand("\\d+\\ *", 0);

        final GrammarElement<String,Map<String,String>> variable =
                new VariableContextManager<>();

        final GrammarElement<String,Map<String,String>> openPar =
                new OpenParenthesis<>("\\(");

        final GrammarElement<String,Map<String,String>> closePar =
                new CloseParenthesis<>("\\)");

        @SuppressWarnings("unchecked")
        final Grammar<String,Map<String,String>> grammar =
                new Grammar<>(operator, number, variable, openPar, closePar);

        final Interpreter<String,Map<String,String>> interpreter =
                new Interpreter<>(grammar);

        final List<Node<String,Map<String,String>>> solution =
                interpreter.parse("@ 1 a (@ 1 2) 3");

        final Map<String,String> context = new HashMap<>();

        final String treePrint = TreePrinter.prettyPrint(solution);
        assertEquals(treePrint, "@ \n" +
                                " 1 \n" +
                                " a \n" +
                                " @ \n" +
                                "  1 \n" +
                                "  2\n" +
                                " \n" +
                                "3\n");
//        System.out.println("treeprint\n" + treePrint);

        final List<String> result =
                PruningSolver.INSTANCE.solve(solution, context);

        assertNull(result);

        final String prunedTreePrint = TreePrinter.prettyPrint(solution);

//        System.out.println("result: " + result);
//        System.out.println("pruned:\n" + prunedTreePrint);

        assertEquals(prunedTreePrint,
                "@ \n" +
                " 1  -> 1 \n" +
                " a \n" +
                " @  -> {@ [1 , 2]}\n" +
                " \n" +
                "3 -> 3\n"
        );
    }
}
