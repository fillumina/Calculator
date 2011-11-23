package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.TestOperator;
import com.fillumina.utils.interpreter.grammar.TestGrammarElement;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class InterpreterTest {

    @Test
    public void shouldCreateASingleNode() {
        final GrammarElement grammarElement = new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(grammarElement);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("123");

        assertEquals(grammarElement, solution.get(0).getGrammarElement());
    }

    @Test
    public void shouldCreateASingleNodeAndMatchTheValue() {
        final GrammarElement grammarElement = new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(grammarElement);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("123");

        assertEquals("123", solution.get(0).getValue());
    }

    @Test
    public void shouldCreateAnOperatorAndAValueNode() {
        final GrammarElement operator = new TestGrammarElement("\\$", 0);
        final GrammarElement number = new TestGrammarElement("[\\d]+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(operator);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("$123");

        assertEquals(operator, solution.get(0).getGrammarElement());
        assertEquals(number, solution.get(1).getGrammarElement());
    }

    @Test
    public void shouldCreateATreeWithOneOperator() {
        final GrammarElement operator =
                new TestOperator("\\*", 0, 1, 1);
        final GrammarElement number =
                new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(operator);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("5*2");

        assertEquals("[{* -> [{5}, {2}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoOperators() {
        final GrammarElement multiply =
                new TestOperator("\\*", 0, 1, 1);
        final GrammarElement minus =
                new TestOperator("\\-", 1, 0, 1);
        final GrammarElement number =
                new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(minus);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("5*-2");

        assertEquals("[{* -> [{5}, {- -> [{2}]}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunctionAndAString() {
        final GrammarElement upper =
                new TestOperator("upper", 1, 0, 1);
        final GrammarElement string =
                new TestGrammarElement("\\'.+\\'", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(string).put(upper);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("upper'hello world'");

        assertEquals("[{upper -> [{'hello world'}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoDualOperators() {
        final GrammarElement multiply =
                new TestOperator("\\*", 1, 1, 1);
        final GrammarElement minus =
                new TestOperator("\\-", 0, 1, 1);
        final GrammarElement number =
                new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(minus);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("2*5-8");

        assertEquals("[{- -> [{* -> [{2}, {5}]}, {8}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoDualOperatorsAndParenthesys() {
        final GrammarElement multiply =
                new TestOperator("\\*", 1, 1, 1);
        final GrammarElement minus =
                new TestOperator("\\-", 0, 1, 1);
        final GrammarElement number =
                new TestGrammarElement("\\d+", 0);
        final GrammarElement openPar =
                new OpenParenthesis("\\(");
        final GrammarElement closePar =
                new CloseParenthesis("\\)");
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(minus).put(openPar).put(closePar);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("2*(5-8)");

        assertEquals("[{* -> [{2}, {- -> [{5}, {8}]}]}]",
                solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunction() {
        final GrammarElement sin =
                new TestOperator("sin", 3, 0, 1);
        final GrammarElement multiply =
                new TestOperator("\\*", 2, 1, 1);
        final GrammarElement minus =
                new TestOperator("\\-", 1, 1, 1);
        final GrammarElement number =
                new TestGrammarElement("\\d+", 0);
        final GrammarElement openPar =
                new OpenParenthesis("\\(");
        final GrammarElement closePar =
                new CloseParenthesis("\\)");
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();

        grammar.put(number).put(multiply).put(sin).put(minus)
                .put(openPar).put(closePar);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("2*sin(5-8)");

        assertEquals("[{* -> [{2}, {sin -> [{- -> [{5}, {8}]}]}]}]",
                solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunctionAndWhiteSpace() {
        final GrammarElement sin = new TestOperator("sin", 3, 0, 1);
        final GrammarElement multiply = new TestOperator("\\*", 2, 1, 1);
        final GrammarElement sum = new TestOperator("\\+", 1, 1, 1);
        final GrammarElement minus = new TestOperator("\\-", 1, 1, 1);
        final GrammarElement number = new TestGrammarElement("\\d+", 0);
        final GrammarElement openPar = new OpenParenthesis("\\(");
        final GrammarElement closePar = new CloseParenthesis("\\)");
        final GrammarElement whiteSpace = new WhiteSpace("[\\ ]+");

        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sin).put(minus).put(sum)
                .put(openPar).put(closePar).put(whiteSpace);

        final Interpreter interpreter = new Interpreter(grammar);
        final List<Node> solution = interpreter.parse("3 * sin( 3 + 5-8)-6");

        assertEquals("[{- -> [{* -> [{3}, {sin -> [{- -> [{+ -> [{3}, {5}]}, {8}]}]}]}, {6}]}]",
                solution.toString());
    }
}