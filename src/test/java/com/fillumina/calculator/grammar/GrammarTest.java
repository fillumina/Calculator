package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.element.ElementMatcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarTest {

    private static final GrammarElementImpl ONE = new GrammarElementImpl(0);
    private static final GrammarElementImpl TWO = new GrammarElementImpl(0);
    private static final GrammarElementImpl THREE = new GrammarElementImpl(0);
    private static final GrammarElementImpl FOUR = new GrammarElementImpl(0);
    private static final GrammarElementImpl FIVE = new GrammarElementImpl(0);
    private static final GrammarElementImpl SIX = new GrammarElementImpl(0);

    @Test
    public void shouldCreateAGrammarWithFixedPositions() {
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> grammar = new Grammar<>(ONE,TWO);
        final List<GrammarElement<String,Void>> list = convertToList(grammar);
        assertContains(list, ONE, TWO);
    }

    @Test
    public void shouldJoinTwoGrammars() {
        final Grammar<String,Void> g1 = new Grammar<>(ONE,TWO);
        final Grammar<String,Void> g2 = new Grammar<>(THREE,FOUR);
        final Grammar<String,Void> joinedGrammars = g1.joinGrammar(g2);
        assertContains(joinedGrammars,ONE,TWO,THREE,FOUR);
    }

    @Test
    public void shouldCreateANewGrammar() {
        @SuppressWarnings("unchecked")
        final Grammar<String,Void> g1 = new Grammar<>(ONE,TWO);
        final Grammar<String,Void> g2 = new Grammar<>(THREE,FOUR);
        final Grammar<String,Void> g3 = new Grammar<String,Void>(g1,g2);
        assertContains(g3, ONE, TWO, THREE, FOUR);
    }

    private static <T> void assertContains(Grammar<T,Void> grammar,
            GrammarElement<T,Void>... array) {
        assertContains(convertToList(grammar), array);
    }

    private static <T> void assertContains(
            List<GrammarElement<T,Void>> list, GrammarElement<T,Void>... array) {
        for (int i=0; i<list.size(); i++) {
            final GrammarElement<T,Void> item = list.get(i);
            final GrammarElement<T,Void> element = array[i];
            if (item == null) {
                throw new NullPointerException("item " + i + " is null!");
            }
            if (!item.equals(element)) {
                throw new AssertionError("element " + i + " expected " +
                        element + " but was " + item);
            }
        }
    }

    private static <T> List<GrammarElement<T,Void>> convertToList(
            final Grammar<T,Void> grammar) {
        final List<GrammarElement<T,Void>> list = new ArrayList<>();
        for (Iterator<GrammarElement<T,Void>> iterator = grammar.iterator();
                iterator.hasNext();) {
            list.add(iterator.next());
        }
        return list;
    }

    private static class GrammarElementImpl
            extends AbstractComparableGrammarElement<String,Void> {

        public GrammarElementImpl(int priority) {
            super(priority);
        }

        @Override
        public GrammarElementMatcher match(String expression) {
            return new ElementMatcher(0, expression.length());
        }

        @Override
        public String evaluate(String value, List<String> params, Void context) {
            return value;
        }

        @Override
        public GrammarElementType getType() {
            return GrammarElementType.UNRECOGNIZED;
        }
    }
}
