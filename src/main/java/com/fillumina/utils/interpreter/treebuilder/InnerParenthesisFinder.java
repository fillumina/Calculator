package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class InnerParenthesisFinder {

    /**
     * Extract a sublist of the inner parenthesis content and put it
     * in the parameters of the open parenthesis (the close one is removed).
     */
    public Node find(final List<Node> nodeList) {
        FinderNode openPar = null;

        final ListIterator<Node> iterator = nodeList.listIterator();
        while (iterator.hasNext()) {
            final FinderNode node = FinderNode.nextFrom(iterator);

            if (node.isAnEmptyOpenParenthesis()) {
                openPar = node;

            } else if (node.isACloseParenthesis()) {
                assertThereIsAnOpenParenthesisBefore(openPar);
                node.remove();
                if (openPar.isPreviousThan(node)) {
                    openPar.remove();
                    openPar = null;
                } else {
                    openPar.fromList(nodeList).extractInnerNodesUpTo(node);
                    return openPar.getNode();
                }
            }
        }
        assertAllOpenParenthesisAreClosed(openPar);
        return null;
    }

    private void assertAllOpenParenthesisAreClosed(final FinderNode openPar) {
        if (openPar != null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private void assertThereIsAnOpenParenthesisBefore(final FinderNode openPar) {
        if (openPar == null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private static class FinderNode extends IndexedNode {

        private static final long serialVersionUID = 1L;
        private final ListIterator<Node> iterator;

        public static FinderNode nextFrom(final ListIterator<Node> iterator) {
            final int index = iterator.nextIndex();
            final Node node = iterator.next();
            return new FinderNode(node, index, iterator);
        }

        private FinderNode(final Node node, final int index,
                final ListIterator<Node> iterator) {
            super(node, index);
            this.iterator = iterator;
        }

        private void remove() {
            iterator.remove();
            iterator.previous();
        }

        private boolean isPreviousThan(final FinderNode node) {
            return getIndex() == node.getIndex() - 1;
        }

        private class Extractor {
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

        private Extractor fromList(final List<Node> nodeList) {
            return new Extractor(nodeList);
        }

        private boolean isACloseParenthesis() {
            return getNode().getGrammarElement() instanceof CloseParenthesis;
        }

        /**
         * An already processed open parenthesis contains as its children
         * all the nodes inside the parenthesis. So if a parenthesis has no
         * nodes it means it is not processed yet.
         */
        private boolean isAnEmptyOpenParenthesis() {
            final Node node = getNode();
            return node.getGrammarElement() instanceof OpenParenthesis &&
                    node.hasNoChildren();
        }
    }
}
