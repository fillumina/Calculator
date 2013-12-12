package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.pattern.WhiteSpace;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

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
        final GrammarElement<String, Void> whiteSpace = new WhiteSpace<>("[\\ ,]+");
        @SuppressWarnings("unchecked")
        final Grammar<String, Void> grammar =
                new Grammar<>(number, multiply, sum, whiteSpace);

        final Tokenizer<String, Void> tokenizer = new Tokenizer<>(grammar);
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