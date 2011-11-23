package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.TestGrammarElement;
import com.fillumina.utils.interpreter.grammar.TestOperator;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.PatternGrammarElement;
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
        final PatternGrammarElement operator = new TestOperator("\\*", 0, 1, 1);
        final PatternGrammarElement number = new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(operator);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12*3*2");

        assertEquals("[{12}, {*}, {3}, {*}, {2}]", list.toString());
    }

    @Test
    public void shouldTokenizeTwoOperations() {
        final PatternGrammarElement multiply = new TestOperator("\\*", 0, 1, 1);
        final PatternGrammarElement sum = new TestOperator("\\+", 0, 1, 1);
        final PatternGrammarElement number = new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12*7+9");

        assertEquals("[{12}, {*}, {7}, {+}, {9}]", list.toString());
    }

    @Test
    public void shouldTokenizeUnrecognizedStrings() {
        final PatternGrammarElement multiply = new TestOperator("\\*", 0, 1, 1);
        final PatternGrammarElement sum = new TestOperator("\\+", 0, 1, 1);
        final PatternGrammarElement number = new TestGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12,3*  7 + 9.33");

        assertEquals(
                "[{12}, {,}, {3}, {*}, {  }, {7}, { }, {+}, { }, {9}, {.}, {33}]",
                list.toString());
    }

}