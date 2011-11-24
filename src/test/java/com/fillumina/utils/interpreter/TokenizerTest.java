package com.fillumina.utils.interpreter;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class TokenizerTest {

    @Test
    public void shouldTokenizeThreeOperands() {
        final GrammarElement<String, Void> operator = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        final Grammar<String, Void> grammar = new Grammar<String, Void>();
        grammar.put(number).put(operator);

        final Tokenizer<String, Void> tokenizer = new Tokenizer<String, Void>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12*3*2");

        assertEquals("[{12}, {*}, {3}, {*}, {2}]", list.toString());
    }

    @Test
    public void shouldTokenizeTwoOperations() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        final Grammar<String, Void> grammar = new Grammar<String, Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer<String, Void> tokenizer = new Tokenizer<String, Void>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12*7+9");

        assertEquals("[{12}, {*}, {7}, {+}, {9}]", list.toString());
    }

    @Test
    public void shouldTokenizeUnrecognizedStrings() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        final Grammar<String, Void> grammar = new Grammar<String, Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer<String, Void> tokenizer = new Tokenizer<String, Void>(grammar);
        final List<Node<String,Void>> list = tokenizer.tokenize("12,3*  7 + 9.33");

        assertEquals(
                "[{12}, {,}, {3}, {*}, {  }, {7}, { }, {+}, { }, {9}, {.}, {33}]",
                list.toString());
    }

}