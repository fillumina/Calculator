package com.fillumina.calculator;

import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.pattern.test.TestOperand;
import com.fillumina.calculator.grammar.pattern.test.TestOperator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TokenizerTest {

    @Test
    public void shouldTokenizeThreeOperands() {
        final GrammarElement<String, Void> operator = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String, Void> grammar = new Grammar<>(number, operator);

        final Tokenizer<String, Void> tokenizer = new DefaultTokenizer<>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12*3*2");

        assertEquals("[{12}, {*}, {3}, {*}, {2}]", list.toString());
    }

    @Test
    public void shouldTokenizeTwoOperations() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String, Void> grammar = new Grammar<>(number, multiply, sum);

        final Tokenizer<String, Void> tokenizer = new DefaultTokenizer<>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12*7+9");

        assertEquals("[{12}, {*}, {7}, {+}, {9}]", list.toString());
    }

    @Test
    public void shouldTokenizeUnrecognizedStrings() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        @SuppressWarnings("unchecked")
        final Grammar<String, Void> grammar =
                new Grammar<>(number, multiply, sum);

        final Tokenizer<String, Void> tokenizer = new DefaultTokenizer<>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12,3*  7 + 9.33");

        assertEquals(
                "[{12}, {,}, {3}, {*}, {  }, {7}, { }, {+}, { }, {9}, {.}, {33}]",
                list.toString());
    }

}