package com.fillumina.calculator.interpreter;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import static com.fillumina.calculator.GrammarElementType.*;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParenthesisCleanerTest {

    @Test
    public void shouldNotTouchAnythingIfThereArentParentheses() {
        final List<Node<String,Void>> list =
                list(node(OPERAND), node(OPERATOR), node(OPERAND));
        ParenthesisCleaner.INSTANCE.executeOn(list);
        assertEquals(3, list.size());
    }

    @Test
    public void shouldMoveTheOnlyContentOfAParenthesesToTheUpperLevel() {
        final List<Node<String,Void>> list =
                list(node(OPERAND), node(OPERATOR),
                        node(OPEN_PAR, node(OPERAND)));
        ParenthesisCleaner.INSTANCE.executeOn(list);
        assertEquals(OPERAND, list.get(2).getType());
    }

    @Test
    public void shouldMoveTheOnlyContentOfAParenthesesToTheUpperLevelRecursively() {
        final List<Node<String,Void>> list =
                list(node(OPERAND), node(OPERATOR),
                        node(OPEN_PAR, node(OPEN_PAR, node(OPERAND))));
        ParenthesisCleaner.INSTANCE.executeOn(list);
        assertEquals(OPERAND, list.get(2).getType());
    }

    @Test
    public void shouldNotPutTheElementOnTheUpperLevel() {
        final List<Node<String,Void>> list =
                list(node(OPERAND), node(OPERATOR),
                        node(OPERATOR, node(OPEN_PAR, node(OPERAND))));
        ParenthesisCleaner.INSTANCE.executeOn(list);
        assertEquals(OPERAND, list.get(2).getChildren().get(0).getType());
    }

    @Test
    public void shouldNotMoveAParenthesesContainingTwoParameters() {
        final List<Node<String,Void>> list =
                list(node(OPERAND), node(OPERATOR),
                        node(OPEN_PAR, node(OPERAND), node(OPERAND)));
        ParenthesisCleaner.INSTANCE.executeOn(list);
        assertEquals(OPEN_PAR, list.get(2).getType());
    }

    private List<Node<String,Void>> list(Node<String,Void>... nodes) {
        final List<Node<String,Void>> list = new ArrayList<>();
        for (Node<String,Void> node: nodes) {
            list.add(node);
        }
        return list;
    }

    private Node<String,Void> node(final GrammarElementType type,
            final Node<String,Void>... nodes) {
        final Node<String,Void> node = new Node<>(type.name(), create(type));
        if (nodes.length > 0) {
            node.addAllChildren(Arrays.asList(nodes));
        }
        return node;
    }

    private static GrammarElement<String,Void> create(
            final GrammarElementType type) {
        return new AbstractComparableGrammarElement<String,Void>(0) {
                private static final long serialVersionUID = 1L;

                @Override
                public GrammarElementMatcher match(
                        GrammarElement<String, Void> previousGrammarElement,
                        String expression) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public String evaluate(String value, List<String> params,
                        Void context) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public GrammarElementType getType() {
                    return type;
                }
            };
    }
}
