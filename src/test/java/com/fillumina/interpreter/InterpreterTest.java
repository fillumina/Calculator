package com.fillumina.interpreter;

import com.fillumina.interpreter.Node;
import com.fillumina.interpreter.DefaultInterpreter;
import com.fillumina.interpreter.grammar.Grammar;
import com.fillumina.interpreter.grammar.pattern.OpenParentheses;
import com.fillumina.interpreter.grammar.pattern.WhiteSpace;
import com.fillumina.interpreter.grammar.pattern.CloseParentheses;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class InterpreterTest {

    @Test
    public void shouldCreateASingleNode() {
        final GrammarElement<String,Void> grammarElement = new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(grammarElement);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("123");

        assertEquals(grammarElement, solution.get(0).getGrammarElement());
    }

    @Test
    public void shouldCreateASingleNodeAndMatchTheValue() {
        final GrammarElement<String,Void> grammarElement =
                new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(grammarElement);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("123");

        assertEquals("123", solution.get(0).getExpression());
    }

    @Test
    public void shouldCreateAnOperatorAndAValueNode() {
        final GrammarElement<String,Void> operator = new TestOperand("\\$", 0);
        final GrammarElement<String,Void> number = new TestOperand("[\\d]+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(operator, number);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("$123");

        assertEquals(operator, solution.get(0).getGrammarElement());
        assertEquals(number, solution.get(1).getGrammarElement());
    }

    @Test
    public void shouldCreateATreeWithOneOperator() {
        final GrammarElement<String,Void> operator =
                new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String,Void> number =
                new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(operator, number);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("5*2");

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
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar =
                new Grammar<>(number, multiply, minus);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("5*-2");

        assertEquals("[{* -> [{5}, {- -> [{2}]}]}]", solution.toString());
    }

    @Test
    public void shouldCreateATreeWithAFunctionAndAString() {
        final GrammarElement<String,Void> upper =
                new TestOperator("upper", 1, 0, 1);
        final GrammarElement<String,Void> string =
                new TestOperand("\\'.+\\'", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(string, upper);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("upper'hello world'");

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
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar =
                new Grammar<>(number, multiply, minus);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("2*5-8");

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
                new OpenParentheses<>("\\(");
        final GrammarElement<String,Void> closePar =
                new CloseParentheses<>("\\)");
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar =
                new Grammar<>(number, multiply, minus, openPar, closePar);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("2*(5-8)");

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
                new OpenParentheses<>("\\(");
        final GrammarElement<String,Void> closePar =
                new CloseParentheses<>("\\)");
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar =
                new Grammar<>(number, multiply, sin, minus, openPar, closePar);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("2*sin(5-8)");

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
        final GrammarElement<String,Void> openPar = new OpenParentheses<>("\\(");
        final GrammarElement<String,Void> closePar = new CloseParentheses<>("\\)");
        final GrammarElement<String,Void> whiteSpace = new WhiteSpace<>("[\\ ]+");

        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(number,
                multiply, sin, minus, sum, openPar, closePar, whiteSpace);

        final Interpreter<String,Void> interpreter = new DefaultInterpreter<>(grammar);
        final List<Node<String,Void>> solution = interpreter.buildSolutionTree("3 * sin( 3 + 5-8)-6");

        assertEquals("[{- -> [{* -> [{3}, {sin -> [{- -> [{+ -> [{3}, {5}]}, {8}]}]}]}, {6}]}]",
                solution.toString());
    }
}