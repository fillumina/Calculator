package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class InnerParenthesisFinder {

    /**
     * Extracts a sublist of the inner parenthesis content and put it
     * in the children of the open parenthesis while the close one is removed.
     */
    public Node find(final List<Node> nodeList) {
        IndexedNode openPar = null;

        final ListIterator<Node> iterator = nodeList.listIterator();
        while (iterator.hasNext()) {
            final IndexedNode node = IndexedNode.nextFrom(iterator);

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
                            .extractInnerNodesTo(openPar);
                    return openPar.getNode();
                }
            }
        }
        assertAllOpenParenthesisAreClosed(openPar);
        return null;
    }

    private void assertAllOpenParenthesisAreClosed(final IndexedNode openPar) {
        if (openPar != null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private void assertThereWasAnOpenParenthesisBefore(final IndexedNode openPar) {
        if (openPar == null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private static class Extractor {
        private final List<Node> nodeList;
        private IndexedNode start, end;

        public Extractor(List<Node> nodeList) {
            this.nodeList = nodeList;
        }

        private Extractor from(final IndexedNode start) {
            this.start = start;
            return this;
        }

        private Extractor to(final IndexedNode end) {
            this.end = end;
            return this;
        }

        private void extractInnerNodesTo(final IndexedNode target) {
            try {
                final int startIdx = start.getIndex() + 1;
                final int endIdx = end.getIndex();
                final List<Node> innerParenthesisList =
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

    private Extractor getList(final List<Node> nodeList) {
        return new Extractor(nodeList);
    }

}
