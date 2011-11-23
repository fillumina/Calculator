package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fra
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Node NULL = new Node(null);

    private final String value;
    private GrammarElement grammarElement;

    @SuppressWarnings("unchecked")
    private List<Node> children = Collections.EMPTY_LIST;

    public Node(final String value) {
        this.value = value;
    }

    public Node(final String value, final GrammarElement grammarElement) {
        this.value = value;
        this.grammarElement = grammarElement;
    }

    public Node addChildren(final Node node) {
        if (children == Collections.EMPTY_LIST) {
            children = new LinkedList<Node>();
        }
        children.add(node);
        return this;
    }

    public Node addAllChildren(final Collection<Node> nodes) {
        for (Node node: nodes) {
            addChildren(node);
        }
        return this;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getValue() {
        return value;
    }

    public GrammarElement getGrammarElement() {
        return grammarElement;
    }

    public void setGrammarElement(final GrammarElement grammarElement) {
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

    public boolean isOfType(final Class<? extends GrammarElement> clazz) {
        return clazz.isInstance(getGrammarElement());
    }

    @Override
    public String toString() {
        return "{" + value +
                (children == Collections.EMPTY_LIST ? "" : (" -> " + children)) + '}';
    }

}
