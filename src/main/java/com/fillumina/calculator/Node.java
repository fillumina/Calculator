package com.fillumina.calculator;

import com.fillumina.calculator.grammar.GrammarElementType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in the solution tree.
 * This class is <i>mutable</i> and not <i>thread safe</i> but it allows
 * <i>deep cloning</i> so that a cloned solution tree can be modified
 * without changing the original.
 *
 * @see UnmodifiableNode
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Node<T,C> implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private final String expression;
    private boolean hasValue;
    private T value;
    private GrammarElement<T,C> grammarElement;

    @SuppressWarnings("unchecked")
    private List<Node<T,C>> children = Collections.EMPTY_LIST;

    public Node(final String expression) {
        this.expression = expression;
    }

    public Node(final String expression,
            final GrammarElement<T,C> grammarElement) {
        this.expression = expression;
        this.grammarElement = grammarElement;
    }

    /** Cloning constructor. */
    public Node(final Node<T,C> other) {
        this.expression = other.expression;
        this.value = other.value;
        this.hasValue = other.hasValue;
        this.grammarElement = other.grammarElement;
        this.children = cloneChildren(other.children);
    }

    protected List<Node<T,C>> cloneChildren(final Collection<Node<T,C>> nodes) {
        final List<Node<T,C>> list = new LinkedList<>();
        for (final Node<T,C> node: nodes) {
            list.add(node.clone());
        }
        return list;
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

    public String getExpression() {
        return expression;
    }

    public T getValue() {
        return value;
    }

    /**
     * Sets the value in the node and removes all its children (they are not
     * needed anymore as parameters to the calculation.
     */
    @SuppressWarnings("unchecked")
    public void setValueAndRemoveChildren(final T value) {
        this.value = value;
        this.hasValue = true;
        children = Collections.EMPTY_LIST;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public GrammarElement<T,C> getGrammarElement() {
        return grammarElement;
    }

    public void setGrammarElement(final GrammarElement<T,C> grammarElement) {
        this.grammarElement = grammarElement;
    }

    public boolean isUnassignedGrammarElement() {
        return grammarElement == null;
    }

    public boolean hasNoChildren() {
        return children.isEmpty();
    }

    public boolean hasChildren() {
        return !hasNoChildren();
    }

    public boolean hasOnlyOneChild() {
        return children.size() == 1;
    }

    public boolean isChildrenNumber(final int number) {
        return children.size() == number;
    }

    public GrammarElementType getType() {
        return grammarElement.getType();
    }

    public boolean isOfType(final GrammarElementType type) {
        return grammarElement == null ? false : grammarElement.getType() == type;
    }

    @Override
    public Node<T,C> clone() {
        return new Node<>(this);
    }

    @Override
    public String toString() {
        return "{" + expression +
            //(grammarElement != null ? " " + grammarElement.getType() : "") +
            (children == Collections.EMPTY_LIST ?
                "" : (" -> " + children)) + '}';
    }
}
