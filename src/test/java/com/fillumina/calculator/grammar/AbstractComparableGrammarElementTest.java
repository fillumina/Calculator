package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractComparableGrammarElementTest {

    private static class ComparableGrammarElement
            extends AbstractComparableGrammarElement<Object, Object> {
        private static final long serialVersionUID = 1L;

        public ComparableGrammarElement(int priority) {
            super(priority);
        }

        @Override
        public GrammarElementMatcher match(
                GrammarElement<Object, Object> previousGrammarElement,
                String expression) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object evaluate(String value, List<Object> params, Object context) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public GrammarElementType getType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    public void shouldReturnThe0Priority() {
        assertEquals(0, new ComparableGrammarElement(0).getPriority());
    }

    @Test
    public void shouldReturnThe1Priority() {
        assertEquals(1, new ComparableGrammarElement(1).getPriority());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void shouldGettingOperandsBeforeThrowAnException() {
        new ComparableGrammarElement(0).getRequiredOperandsBefore();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void shouldGettingOperandsAfterThrowAnException() {
        new ComparableGrammarElement(0).getRequiredOperandsAfter();
    }

    @Test(expected=ClassCastException.class)
    public void shouldNotBeComparableWithOtherClasses() {
        GrammarElement<Object,Object> ge = new ComparableGrammarElement(0);
        GrammarElement<Object,Object> comparableGrammarElement =
                new GrammarElement<Object,Object>() {

            @Override
            public GrammarElementMatcher match(
                    GrammarElement<Object, Object> previousGrammarElement,
                    String expression) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object evaluate(String value, List<Object> params,
                    Object context) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getRequiredOperandsAfter() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getRequiredOperandsBefore() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public GrammarElementType getType() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int compareTo(GrammarElement<Object, Object> o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        ge.compareTo(comparableGrammarElement);
    }
}
