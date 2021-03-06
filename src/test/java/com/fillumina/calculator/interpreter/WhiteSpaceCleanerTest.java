package com.fillumina.calculator.interpreter;

import com.fillumina.calculator.DefaultTokenizer;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.TestOperand;
import com.fillumina.calculator.TestOperator;
import com.fillumina.calculator.Tokenizer;
import com.fillumina.calculator.element.WhiteSpace;
import com.fillumina.calculator.grammar.Grammar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpaceCleanerTest {

    private WhiteSpaceCleaner whiteSpaceCleaner =
            WhiteSpaceCleaner.INSTANCE;

    @Test
    public void shouldCleanTheWhiteSpaces() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        final GrammarElement<String, Void> whiteSpace = new WhiteSpace<>(0, " ,+");
        @SuppressWarnings("unchecked")
        final Grammar<String, Void> grammar =
                new Grammar<>(number, multiply, sum, whiteSpace);

        final Tokenizer<String, Void> tokenizer = new DefaultTokenizer<>(grammar);
        final List<Node<String, Void>> list = tokenizer.tokenize("12,3*  7 + 9.33");

        assertEquals(
                "[{12}, {,}, {3}, {*}, {  }, {7}, { }, {+}, { }, {9}, {.}, {33}]",
                list.toString());

        whiteSpaceCleaner.executeOn(list);

        assertEquals(
                "[{12}, {3}, {*}, {7}, {+}, {9}, {.}, {33}]",
                list.toString());

    }

}