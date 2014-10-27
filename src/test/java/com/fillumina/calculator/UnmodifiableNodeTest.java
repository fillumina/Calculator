package com.fillumina.calculator;

import com.fillumina.calculator.grammar.GrammarElementType;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UnmodifiableNodeTest {

    private Node<Integer,Void> parentNode;

    @Before
    public void initNode() {
        parentNode = new Node<>("parent");
        parentNode.addChildren(new Node<Integer,Void>("children1"));
        parentNode.addChildren(new Node<Integer,Void>("children2"));
    }

    @Test
    public void shouldTheGrammarElementOfOriginalNodeBeSettable() {
        parentNode.setGrammarElement(new GrammarElementImpl());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldTheUnmodifiableNodeGrammarElementNotBeingSettable() {
        new UnmodifiableNode<>(parentNode)
                .setGrammarElement(new GrammarElementImpl());
    }

    @Test
    public void shouldTheOriginalOneAcceptsNewChildren() {
        parentNode.addChildren(new Node<Integer,Void>("another"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldTheUnmodifiableOneNotAcceptNewChildren() {
        new UnmodifiableNode<>(parentNode)
                .addChildren(new Node<Integer,Void>("another"));
    }

    @Test
    public void shouldTheOriginalOneAcceptsACollectionOfNodes() {
        parentNode.addAllChildren(
                Arrays.asList(new Node<Integer,Void>("another")));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldTheUnmodifiableOneNotAcceptACollectionOfNodes() {
        new UnmodifiableNode<>(parentNode).addAllChildren(
                Arrays.asList(new Node<Integer,Void>("another")));
    }

    @Test
    public void shouldTheOriginalOneChangeTheValue() {
        parentNode.setValueAndRemoveChildren(12);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldTheUnmodifiableOneCannotChangeTheValue() {
        new UnmodifiableNode<>(parentNode).setValueAndRemoveChildren(12);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldTheChildrenOfTheUnmodifiableOneBeUnmodifiableAsWell() {
        new UnmodifiableNode<>(parentNode).getChildren().get(0)
                .setValueAndRemoveChildren(12);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldACloneOfTheUnmodifiableOneBeUnmodifiable() {
        new UnmodifiableNode<>(parentNode).clone()
                .setValueAndRemoveChildren(12);
    }

    private static class GrammarElementImpl implements GrammarElement<Integer, Void> {

        public GrammarElementImpl() {
        }

        @Override
        public GrammarElementMatcher match(String expression) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Integer evaluate(String value, List<Integer> params,
                Void context) {
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
        public int compareTo(GrammarElement<Integer, Void> o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
