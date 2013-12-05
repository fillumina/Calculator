package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in a tree.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Node<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Node<?,?> NULL = new Node<>(null);

    private final String value;
    // TODO inherit a GrammarNode with grammarElement defined?
    private GrammarElement<T,C> grammarElement;

    @SuppressWarnings("unchecked")
    private List<Node<T,C>> children = Collections.EMPTY_LIST;

    public Node(final String value) {
        this.value = value;
    }

    public Node(final String value, final GrammarElement<T,C> grammarElement) {
        this.value = value;
        this.grammarElement = grammarElement;
    }

    public Node<T,C> addChildren(final Node<T,C> node) {
        if (children == Collections.EMPTY_LIST) {
            children = new LinkedList<>();
        }
        children.add(node);
        return this;
    }

    public Node<T,C> addAllChildren(final Collection<Node<T,C>> nodes) {
        for (Node<T,C> node: nodes) {
            addChildren(node);
        }
        return this;
    }

    public List<Node<T,C>> getChildren() {
        return children;
    }

    public String getValue() {
        return value;
    }

    public GrammarElement<T,C> getGrammarElement() {
        return grammarElement;
    }

    public void setGrammarElement(final GrammarElement<T,C> grammarElement) {
        this.grammarElement = grammarElement;
    }

    public boolean isUnrecognized() {
        return grammarElement == null;
    }

    public boolean hasNoChildren() {
        return getChildren().isEmpty();
    }

    public boolean hasChildren() {
        return !hasNoChildren();
    }

    public boolean hasOnlyOneChild() {
        return getChildren().size() == 1;
    }

    public boolean isChildrenNumber(final int number) {
        return getChildren().size() == number;
    }

    public boolean isOfType(final GrammarElement.Type type) {
        final GrammarElement<T, C> ge = getGrammarElement();
        return ge == null ? false : ge.isType(type);
    }

    @Override
    public String toString() {
        return "{" + value +
            (children == Collections.EMPTY_LIST ?
                "" : (" -> " + children)) + '}';
    }
}
