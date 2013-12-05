package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class HigherPriorityOperatorFinder {

    public static final HigherPriorityOperatorFinder INSTANCE =
            new HigherPriorityOperatorFinder();

    public <T,C> IndexedNode<T,C> find(final List<Node<T,C>> list) {
        @SuppressWarnings("unchecked")
        IndexedNode<T,C> higherPriorityNode = (IndexedNode<T,C>) IndexedNode.NULL;

        if (list.size() > 1) {
            final ListIterator<Node<T,C>> iterator = list.listIterator();
            while (iterator.hasNext()) {
                final IndexedNode<T,C> currentNode = IndexedNode.getFrom(iterator);

                if (currentNode.isEmptyOperator() &&
                        (higherPriorityNode.isNull() ||
                        higherPriorityNode.lessThan(currentNode))) {

                    higherPriorityNode = currentNode;
                }
            }
        }
        return higherPriorityNode;
    }
}
