package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class InnerParenthesisFinder<T,C> {

    /**
     * Extracts a sublist of the inner parenthesis content and put it
     * in the children of the open parenthesis while the close one is removed.
     */
    public Node<T,C> find(final List<Node<T,C>> nodeList) {
        IndexedNode openPar = null;

        final ListIterator<Node<T,C>> iterator = nodeList.listIterator();
        while (iterator.hasNext()) {
            final IndexedNode<T,C> node = IndexedNode.nextFrom(iterator);

            if (node.isAnEmptyOpenParenthesis()) {
                openPar = node;

            } else if (node.isACloseParenthesis()) {
                assertThereWasAnOpenParenthesisBefore(openPar);
                node.remove();
                if (openPar.isPreviousThan(node)) {
                    openPar.remove();
                    openPar = null;
                } else {
                    getList(nodeList).from(openPar).to(node)
                            .extractInnerNodesToChildrenOf(openPar);
                    return openPar.getNode();
                }
            }
        }
        assertAllOpenParenthesisAreClosed(openPar);
        return null;
    }

    private void assertAllOpenParenthesisAreClosed(final IndexedNode<T,C> openPar) {
        if (openPar != null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private void assertThereWasAnOpenParenthesisBefore(final IndexedNode<T,C> openPar) {
        if (openPar == null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private static class Extractor<T,C> {
        private final List<Node<T,C>> nodeList;
        private IndexedNode start, end;

        public Extractor(List<Node<T,C>> nodeList) {
            this.nodeList = nodeList;
        }

        private Extractor from(final IndexedNode<T,C> start) {
            this.start = start;
            return this;
        }

        private Extractor to(final IndexedNode<T,C> end) {
            this.end = end;
            return this;
        }

        private void extractInnerNodesToChildrenOf(final IndexedNode<T,C> target) {
            try {
                final int startIdx = start.getIndex() + 1;
                final int endIdx = end.getIndex();
                final List<Node<T,C>> innerParenthesisList =
                        nodeList.subList(startIdx, endIdx);
                target.getNode().addAllChildren(innerParenthesisList);
                // NOTE: removing from a sublist removes from the list
                // it belongs from (a sublist is a view of the belonging-from list)
                innerParenthesisList.clear();
            } catch (IndexOutOfBoundsException e) {
                throw new ParenthesisMismatchedException(e);
            }
        }
    }

    private Extractor<T,C> getList(final List<Node<T,C>> nodeList) {
        return new Extractor(nodeList);
    }

}
