package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.TestGrammarElement;
import com.fillumina.utils.interpreter.grammar.TestOperator;
import org.junit.Before;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.PatternGrammarElement;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class WhiteSpaceCleanerTest {

    private WhiteSpaceCleaner whiteSpaceCleaner;

    @Before
    public void init() {
        whiteSpaceCleaner = new WhiteSpaceCleaner();
    }

    @Test
    public void shouldCleanTheWhiteSpaces() {
        final PatternGrammarElement multiply = new TestOperator("\\*", 0, 1, 1);
        final PatternGrammarElement sum = new TestOperator("\\+", 0, 1, 1);
        final PatternGrammarElement number = new TestGrammarElement("\\d+", 0);
        final PatternGrammarElement whiteSpace = new WhiteSpace("\\ +");
        final Grammar<Node,Void> grammar = new Grammar<Node,Void>();
        grammar.put(number).put(multiply).put(sum).put(whiteSpace);

        final Tokenizer tokenizer = new Tokenizer(grammar);
        final List<Node> list = tokenizer.tokenize("12,3*  7 + 9.33");

        whiteSpaceCleaner.clean(list);

        assertEquals(
                "[{12}, {,}, {3}, {*}, {7}, {+}, {9}, {.}, {33}]",
                list.toString());

    }

}