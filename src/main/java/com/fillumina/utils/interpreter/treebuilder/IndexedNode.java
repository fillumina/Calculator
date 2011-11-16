package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.Operator;
import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * It's an helper bean that contains both the node and node's index
 * in a given list.
 *
 * @author fra
 */
class IndexedNode implements Comparable<Node>, Serializable {

    private static final long serialVersionUID = 1L;
    public static final IndexedNode NULL =
            new IndexedNode(null, Integer.MIN_VALUE, null);

    private final Node node;
    private final int index;
    private final ListIterator<Node> iterator;

    public static IndexedNode nextFrom(final ListIterator<Node> iterator) {
        final int index = iterator.nextIndex();
        final Node node = iterator.next();
        return new IndexedNode(node, index, iterator);
    }

    public IndexedNode(final Node node, final int index,
            final ListIterator<Node> iterator) {
        this.node = node;
        this.index = index;
        this.iterator = iterator;
    }

    public int getIndex() {
        return index;
    }

    public Node getNode() {
        return node;
    }

    public void remove() {
        iterator.remove();
        iterator.previous();
    }

    public class Extractor {

        private final List<Node> nodeList;

        public Extractor(List<Node> nodeList) {
            this.nodeList = nodeList;
        }

        private void extractInnerNodesUpTo(final IndexedNode upTo) {
            try {
                final int start = getIndex() + 1;
                final int end = upTo.getIndex();
                final List<Node> innerParenthesisList =
                        nodeList.subList(start, end);
                getNode().addAllChildren(innerParenthesisList);
                // NOTE: removing from a sublist removes from the list
                // it belongs from (a sublist is a view of the belonging-from list)
                innerParenthesisList.clear();
            } catch (IndexOutOfBoundsException e) {
                throw new ParenthesisMismatchedException(e);
            }
        }
    }

    public Extractor fromList(final List<Node> nodeList) {
        return new Extractor(nodeList);
    }

    public boolean isPreviousThan(final IndexedNode node) {
        return getIndex() == node.getIndex() - 1;
    }

    public boolean isACloseParenthesis() {
        return getNode().getGrammarElement() instanceof CloseParenthesis;
    }

    /**
     * An already processed open parenthesis contains as its children
     * all the nodes inside the parenthesis. So if a parenthesis has no
     * nodes it means it is not processed yet.
     */
    public boolean isAnEmptyOpenParenthesis() {
        final Node node = getNode();
        return node.getGrammarElement() instanceof OpenParenthesis &&
                node.hasNoChildren();
    }

    public boolean lessThan(final IndexedNode o) {
        return compareTo(o.getNode()) < 0;
    }

    public boolean isNull() {
        return NULL == this;
    }

    public boolean isEmptyOperator() {
        return getNode().getGrammarElement() instanceof Operator<?, ?> &&
                getNode().hasNoChildren();
    }

    @Override
    public int compareTo(final Node o) {
        final GrammarElement ge = o.getGrammarElement();
        return this.node.getGrammarElement().compareTo(ge);
    }

    @Override
    public String toString() {
        return "IndexedNode{" + "node=" + node + ", index=" + index + '}';
    }
}
