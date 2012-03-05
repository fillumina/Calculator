package com.fillumina.utils.interpreter;

import org.junit.Before;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class WhiteSpaceCleanerTest {

    private WhiteSpaceCleaner<String, Void> whiteSpaceCleaner;

    @Before
    public void init() {
        whiteSpaceCleaner = new WhiteSpaceCleaner<>();
    }

    @Test
    public void shouldCleanTheWhiteSpaces() {
        final GrammarElement<String, Void> multiply = new TestOperator("\\*", 0, 1, 1);
        final GrammarElement<String, Void> sum = new TestOperator("\\+", 0, 1, 1);
        final GrammarElement<String, Void> number = new TestOperand("\\d+", 0);
        final GrammarElement<String, Void> whiteSpace = new WhiteSpace<>("[\\ ,]+");
        final Grammar<String, Void> grammar = new Grammar<>();
        grammar.put(number).put(multiply).put(sum).put(whiteSpace);

        final Tokenizer<String, Void> tokenizer = new Tokenizer<>(grammar);
        final List<Node<String, Void>> list = tokenizer.tokenize("12,3*  7 + 9.33");

        whiteSpaceCleaner.clean(list);

        assertEquals(
                "[{12}, {3}, {*}, {7}, {+}, {9}, {.}, {33}]",
                list.toString());

    }

}