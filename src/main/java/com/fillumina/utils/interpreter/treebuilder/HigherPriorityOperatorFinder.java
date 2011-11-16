package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class HigherPriorityOperatorFinder {

    public IndexedNode findIndex(final List<Node> list) {
        IndexedNode higherPriorityNode = IndexedNode.NULL;

        if (list.size() > 1) {
            final ListIterator<Node> iterator = list.listIterator();
            while (iterator.hasNext()) {
                final IndexedNode currentNode = IndexedNode.nextFrom(iterator);

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
