package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.NotExecutableGrammarElement;
import com.fillumina.utils.interpreter.grammar.NotExecutableOperator;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
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
        final GrammarElement operator = new NotExecutableOperator("\\*", 0, 1, 1);
        final GrammarElement number = new NotExecutableGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(operator);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12*3*2");

        assertEquals("[{12}, {*}, {3}, {*}, {2}]", list.toString());
    }

    @Test
    public void shouldTokenizeTwoOperations() {
        final GrammarElement multiply = new NotExecutableOperator("\\*", 0, 1, 1);
        final GrammarElement sum = new NotExecutableOperator("\\+", 0, 1, 1);
        final GrammarElement number = new NotExecutableGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12*7+9");

        assertEquals("[{12}, {*}, {7}, {+}, {9}]", list.toString());
    }

    @Test
    public void shouldTokenizeUnrecognizedStrings() {
        final GrammarElement multiply = new NotExecutableOperator("\\*", 0, 1, 1);
        final GrammarElement sum = new NotExecutableOperator("\\+", 0, 1, 1);
        final GrammarElement number = new NotExecutableGrammarElement("\\d+", 0);
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sum);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12,3*  7 + 9.33");

        assertEquals(
                "[{12}, {,}, {3}, {*}, {  }, {7}, { }, {+}, { }, {9}, {.}, {33}]",
                list.toString());
    }

}