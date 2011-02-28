package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.Operator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class HigherPriorityOperatorFinder {
    public int findIndex(final List<Node> list) {
        IndexedNode higherPriorityNode = IndexedNode.NULL;

        if (list.size() > 1) {
            final ListIterator<Node> iterator = list.listIterator();
            while (iterator.hasNext()) {
                final Node currentNode = iterator.next();

                if (isEmptyOperator(currentNode) &&
                        (isNull(higherPriorityNode) ||
                        higherPriorityNode.lessThan(currentNode))) {

                    higherPriorityNode =
                            new IndexedNode(currentNode,
                            iterator.previousIndex());
                }
            }
        }
        return higherPriorityNode.getIndex();
    }

    private boolean isNull(final IndexedNode higherPriorityNode) {
        return IndexedNode.NULL == higherPriorityNode;
    }

    private boolean isEmptyOperator(final Node currentNode) {
        return currentNode.getGrammarElement() instanceof Operator<?, ?> &&
                currentNode.hasNoParameters();
    }
}
