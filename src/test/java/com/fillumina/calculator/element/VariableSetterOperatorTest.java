package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VariableSetterOperatorTest extends GrammarElementTestBase {

    private GrammarElement<String,Map<String,String>> variableSetter =
            new VariableSetterOperator<>(0);

    @Override
    protected GrammarElement<?, ?> getGrammarElement() {
        return variableSetter;
    }

    @Test
    public void shouldMatchTheSetter() {
        recognize("var =", "var = (123 + 3)");
    }

    @Test
    public void shouldMatchTheVariableWithNumber() {
        recognize("test2 =", "test2 = 123");
    }

    @Test
    public void shouldMatchTheVarNameWithANumberBefore() {
        recognize("test =", "12test = 123");
    }

    @Test
    public void shouldNotRecognizeTheDifferentOperator() {
        notRecognize("true != false");
    }

    @Test
    public void shouldNotRecognizeIfThereIsANumberBefore() {
        notRecognize("12 = 3");
    }

    @Test
    public void shouldNotRecognizeIfThereIsAParenthesesBefore() {
        notRecognize("(12 + 3) = 4");
    }
}
