package com.fillumina.interpreter.grammar.treebuilder;

import com.fillumina.interpreter.grammar.GrammarElementType;
import com.fillumina.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 * Selects the {@link Node} in the given list containing the
 * {@link GrammarElement} with the highest priority.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class HigherPriorityOperatorFinder {

    static final HigherPriorityOperatorFinder INSTANCE =
            new HigherPriorityOperatorFinder();

    private HigherPriorityOperatorFinder() {}

    /**
     * Selects the {@link Node} in the given list containing the
     * {@link GrammarElement} with the highest priority.
     *
     * @return a {@link IndexedNode} containing the highest {@link GrammarElement}
     *         or {@link IndexedNode#NULL}.
     */
    public <T,C> IndexedNode<T,C> find(final List<Node<T,C>> list) {
        Node<T,C> higherPriorityNode = null;
        int higherPriorityNodeIndex = -1;

        if (list.size() > 1) {
            int index = -1;
            final ListIterator<Node<T,C>> iterator = list.listIterator();
            while (iterator.hasNext()) {
                index++;
                final Node<T,C> currentNode = iterator.next();

                if (isEmptyOperator(currentNode) &&
                        (higherPriorityNode == null ||
                        isLessThan(higherPriorityNode, currentNode))) {

                    higherPriorityNode = currentNode;
                    higherPriorityNodeIndex = index;
                }
            }
        }
        if (higherPriorityNode == null) {
            return IndexedNode.getNull();
        }
        return new IndexedNode<>(higherPriorityNode,
                higherPriorityNodeIndex, null);
    }

    private <T,C> boolean isEmptyOperator(final Node<T,C> node) {
        final GrammarElementType type = node.getGrammarElement().getType();
        return type == GrammarElementType.OPERATOR && node.hasNoChildren();
    }

    private <T,C> boolean isLessThan(final Node<T,C> a, final Node<T,C> b) {
        return a.getGrammarElement().compareTo(b.getGrammarElement()) < 0;
    }
}
