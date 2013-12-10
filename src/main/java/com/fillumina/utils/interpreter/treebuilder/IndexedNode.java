package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.Node;
import java.io.Serializable;
import java.util.ListIterator;

/**
 * It's an helper that contains both the node and node index
 * in a given list.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class IndexedNode<T,C> implements Comparable<Node<T,C>>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final IndexedNode<?,?> NULL =
            new IndexedNode<>(null, Integer.MIN_VALUE, null);

    private final Node<T,C> node;
    private final int index;
    private final ListIterator<Node<T,C>> iterator;

    public static <T,C> IndexedNode<T,C> getFrom(
            final ListIterator<Node<T,C>> iterator) {
        final int index = iterator.nextIndex();
        final Node<T,C> node = iterator.next();
        return new IndexedNode<>(node, index, iterator);
    }

    public IndexedNode(final Node<T,C> node, final int index,
            final ListIterator<Node<T,C>> iterator) {
        this.node = node;
        this.index = index;
        this.iterator = iterator;
    }

    public int getIndex() {
        return index;
    }

    public Node<T,C> getNode() {
        return node;
    }

    public void remove() {
        iterator.remove();
        iterator.previous();
    }

    public boolean isPreviousThan(final IndexedNode<T,C> node) {
        return getIndex() == node.getIndex() - 1;
    }

    public boolean isACloseParenthesis() {
        return getNode().getGrammarElement().getType() ==
                GrammarElementType.CLOSED_PAR;
    }

    /**
     * An already processed open parenthesis contains as its children
     * all the nodes inside the parenthesis. So if a parenthesis has no
     * nodes it means it is not processed yet.
     */
    public boolean isAnEmptyOpenParenthesis() {
        final Node<T,C> n = getNode();
        return n.getGrammarElement().getType() == GrammarElementType.OPEN_PAR &&
                n.hasNoChildren();
    }

    public boolean lessThan(final IndexedNode<T,C> o) {
        return compareTo(o.getNode()) < 0;
    }

    public boolean isNull() {
        return NULL == this;
    }

    public boolean isEmptyOperator() {
        final GrammarElementType type = getNode().getGrammarElement().getType();
        return type == GrammarElementType.OPERATOR &&
                getNode().hasNoChildren();
    }

    @Override
    public int compareTo(final Node<T,C> o) {
        final GrammarElement<T,C> ge = o.getGrammarElement();
        return this.node.getGrammarElement().compareTo(ge);
    }

    @Override
    public String toString() {
        return "IndexedNode{" + "node=" + node + ", index=" + index + '}';
    }
}
