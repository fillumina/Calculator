package com.fillumina.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a node in the solution tree. This {@link Node} is read only
 * and can be passed around.
 *
 * @see Node
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UnmodifiableNode<T,C> extends Node<T,C>
        implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    /** Takes a solution and returns a unmodifiable version of it. */
    public static <T,C> List<Node<T,C>> unmodifiable(
            final List<Node<T,C>> solution) {
        final List<Node<T,C>> list = new ArrayList<>(solution.size());
        for (Node<T,C> node : solution) {
            list.add(new UnmodifiableNode<>(node));
        }
        return Collections.unmodifiableList(list);
    }

    /** Cloning constructor. */
    public UnmodifiableNode(Node<T,C> other) {
        super(other);
    }

    @Override
    protected List<Node<T,C>> cloneChildren(Collection<Node<T, C>> nodes) {
        final List<Node<T,C>> list = new ArrayList<>(nodes.size());
        for (final Node<T,C> node: nodes) {
            list.add(new UnmodifiableNode<>(node));
        }
        return Collections.unmodifiableList(list);
    }

    /** Does nothing at all. */
    @Override
    public UnmodifiableNode<T,C> addChildren(Node<T,C> node) {
        return this;
    }

    /** Does nothing at all. */
    @Override
    public UnmodifiableNode<T,C> addAllChildren(Collection<Node<T,C>> nodes) {
        return this;
    }

    /**
     * Sets the value in the node and removes all its children (they are not
     * needed anymore as parameters to the calculation.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setValueAndRemoveChildren(T value) {
        throw new IllegalStateException("this is an unmodifiable Node");
    }

    @Override
    public void setGrammarElement(GrammarElement<T,C> grammarElement) {
        throw new IllegalStateException("this is an unmodifiable Node");
    }

    @Override
    public UnmodifiableNode<T,C> clone() {
        return new UnmodifiableNode<>(this);
    }
}
