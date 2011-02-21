package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.GrammarElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author fra
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;
    private GrammarElement grammarElement;

    @SuppressWarnings("unchecked")
    private List<Node> nodes = Collections.EMPTY_LIST;

    public Node(final String value) {
        this.value = value;
    }

    public Node(final String value, final GrammarElement grammarElement) {
        this.value = value;
        this.grammarElement = grammarElement;
    }

    public String getValue() {
        return value;
    }

    public boolean isUnrecognized() {
        return grammarElement == null;
    }

    public boolean hasNoParameters() {
        return nodes == Collections.EMPTY_LIST;
    }

    public GrammarElement getGrammarElement() {
        return grammarElement;
    }

    public void setGrammarElement(final GrammarElement grammarElement) {
        this.grammarElement = grammarElement;
    }

    public Node add(final Node node) {
        if (nodes == Collections.EMPTY_LIST) {
            nodes = new ArrayList<Node>();
        }
        nodes.add(node);
        return this;
    }

    public Node addAll(final Collection<Node> nodes) {
        for (Node node: nodes) {
            add(node);
        }
        return this;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "{" + value + 
                (nodes == Collections.EMPTY_LIST ? "" : (" -> " + nodes)) + '}';
    }

    public boolean hasChildren() {
        return getNodes().size() != 0;
    }

    public boolean hasOnlyOneChild() {
        return getNodes().size() == 1;
    }

    public boolean isChildrenNumber(final int number) {
        return getNodes().size() == number;
    }

    public boolean isOfType(final Class<? extends GrammarElement> clazz) {
        return clazz.isInstance(getGrammarElement());
    }

}
