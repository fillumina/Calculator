package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
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
        final GrammarElement<String,Void> grammarElement = new TestOperand("\\d+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(grammarElement);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("123");

        assertEquals(grammarElement, solution.get(0).getGrammarElement());
    }

    @Test
    public void shouldCreateASingleNodeAndMatchTheValue() {
        final GrammarElement<String,Void> grammarElement = new TestOperand("\\d+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(grammarElement);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("123");

        assertEquals("123", solution.get(0).getValue());
    }

    @Test
    public void shouldCreateAnOperatorAndAValueNode() {
        final GrammarElement<String,Void> operator = new TestOperand("\\$", 0);
        final GrammarElement<String,Void> number = new TestOperand("[\\d]+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(operator);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("$123");

        assertEquals(operator, solution.get(0).getGrammarElement());
        assertEquals(number, solution.get(1).getGrammarElement());
    }

    @Test
    public void shouldCreateATreeWithOneOperator() {
        final GrammarElement<String,Void> operator =
                new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(operator);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("5*2");

        assertEquals("[{* -> [{5}, {2}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoOperators() {
        final GrammarElement<String,Void> multiply =
                new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String,Void> minus =
                new TestOperator("\\-", 1, 0, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(multiply).put(minus);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("5*-2");

        assertEquals("[{* -> [{5}, {- -> [{2}]}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunctionAndAString() {
        final GrammarElement<String,Void> upper =
                new TestOperator("upper", 1, 0, 1);
        final GrammarElement<String,Void> string =
                new TestOperand("\\'.+\\'", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(string).put(upper);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("upper'hello world'");

        assertEquals("[{upper -> [{'hello world'}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoDualOperators() {
        final GrammarElement<String,Void> multiply =
                new TestOperator("\\*", 1, 1, 1);
        final GrammarElement<String,Void> minus =
                new TestOperator("\\-", 0, 1, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(multiply).put(minus);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("2*5-8");

        assertEquals("[{- -> [{* -> [{2}, {5}]}, {8}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithTwoDualOperatorsAndParenthesys() {
        final GrammarElement<String,Void> multiply =
                new TestOperator("\\*", 1, 1, 1);
        final GrammarElement<String,Void> minus =
                new TestOperator("\\-", 0, 1, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        final GrammarElement<String,Void> openPar =
                new OpenParenthesis("\\(");
        final GrammarElement<String,Void> closePar =
                new CloseParenthesis("\\)");
        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(multiply).put(minus).put(openPar).put(closePar);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("2*(5-8)");

        assertEquals("[{* -> [{2}, {- -> [{5}, {8}]}]}]",
                solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunction() {
        final GrammarElement<String,Void> sin =
                new TestOperator("sin", 3, 0, 1);
        final GrammarElement<String,Void> multiply =
                new TestOperator("\\*", 2, 1, 1);
        final GrammarElement<String,Void> minus =
                new TestOperator("\\-", 1, 1, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        final GrammarElement<String,Void> openPar =
                new OpenParenthesis("\\(");
        final GrammarElement<String,Void> closePar =
                new CloseParenthesis("\\)");
        final Grammar<String,Void> grammar = new Grammar<String,Void>();

        grammar.put(number).put(multiply).put(sin).put(minus)
                .put(openPar).put(closePar);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("2*sin(5-8)");

        assertEquals("[{* -> [{2}, {sin -> [{- -> [{5}, {8}]}]}]}]",
                solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunctionAndWhiteSpace() {
        final GrammarElement<String,Void> sin = new TestOperator("sin", 3, 0, 1);
        final GrammarElement<String,Void> multiply = new TestOperator("\\*", 2, 1, 1);
        final GrammarElement<String,Void> sum = new TestOperator("\\+", 1, 1, 1);
        final GrammarElement<String,Void> minus = new TestOperator("\\-", 1, 1, 1);
        final GrammarElement<String,Void> number = new TestOperand("\\d+", 0);
        final GrammarElement<String,Void> openPar = new OpenParenthesis<String,Void>("\\(");
        final GrammarElement<String,Void> closePar = new CloseParenthesis<String,Void>("\\)");
        final GrammarElement<String,Void> whiteSpace = new WhiteSpace<String,Void>("[\\ ]+");

        final Grammar<String,Void> grammar = new Grammar<String,Void>();
        grammar.put(number).put(multiply).put(sin).put(minus).put(sum)
                .put(openPar).put(closePar).put(whiteSpace);

        final Interpreter<String,Void> interpreter = new Interpreter<String,Void>(grammar);
        final List<Node<String,Void>> solution = interpreter.parse("3 * sin( 3 + 5-8)-6");

        assertEquals("[{- -> [{* -> [{3}, {sin -> [{- -> [{+ -> [{3}, {5}]}, {8}]}]}]}, {6}]}]",
                solution.toString());
    }
}