package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 * Extracts the content of a couple of opened and closed parentheses
 * putting the contained nodes as the children
 * of the opened parentheses and removing the closed parentheses node.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class InnerParenthesisFinder {

    static final InnerParenthesisFinder INSTANCE = new InnerParenthesisFinder();

    /**
     * Extracts a sublist with the nodes contained between an opened and a
     * closed parentheses and put them as the children of the open parenthesis
     * while the node containing the closed one is removed.
     *
     * @return the {@link Node} containing the opened parentheses or
     *         {@code null} if no opened parentheses are found.
     */
    public <T,C> Node<T,C> find(final List<Node<T,C>> nodeList) {
        IndexedNode<T,C> openPar = null;

        final ListIterator<Node<T,C>> iterator = nodeList.listIterator();
        while (iterator.hasNext()) {
            final IndexedNode<T,C> node =
                    IndexedNode.createFromNextItemIn(iterator);

            if (node.isAnEmptyOpenParenthesis()) {
                openPar = node;

            } else if (node.isACloseParenthesis()) {
                assertThereWasAnOpenParenthesisBefore(openPar);
                node.remove();
                if (isEmptyParentheses(openPar, node)) {
                    openPar.remove();
                    openPar = null;
                } else {
                    extractFromList(nodeList).start(openPar).end(node)
                            .moveInnerNodesAsChildrenOf(openPar);

                    return openPar.getNode();
                }
            }
        }
        assertAllOpenParenthesisAreClosed(openPar);
        return null;
    }

    /** There is no content between the open and the closed parentheses. */
    private static <T,C> boolean isEmptyParentheses(
            final IndexedNode<T, C> openPar,
            final IndexedNode<T, C> node) {
        return openPar.isPreviousThan(node);
    }

    private <T,C> void assertAllOpenParenthesisAreClosed(
            final IndexedNode<T,C> openPar) {
        if (openPar != null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private <T,C> void assertThereWasAnOpenParenthesisBefore(
            final IndexedNode<T,C> openPar) {
        if (openPar == null) {
            throw new ParenthesisMismatchedException();
        }
    }

    private static class Extractor<T,C> {
        private final List<Node<T,C>> nodeList;
        private IndexedNode<T,C> start, end;

        public Extractor(List<Node<T,C>> nodeList) {
            this.nodeList = nodeList;
        }

        private Extractor<T,C> start(final IndexedNode<T,C> start) {
            this.start = start;
            return this;
        }

        private Extractor<T,C> end(final IndexedNode<T,C> end) {
            this.end = end;
            return this;
        }

        private void moveInnerNodesAsChildrenOf(final IndexedNode<T,C> target) {
            try {
                final int startIdx = start.getIndex() + 1;
                final int endIdx = end.getIndex();
                final List<Node<T,C>> innerParenthesisList =
                        nodeList.subList(startIdx, endIdx);
                target.getNode().addAllChildren(innerParenthesisList);
                // NOTE: removing from a sublist removes from the list
                // it belongs from (a sublist is a view of the belonging list)
                innerParenthesisList.clear();
            } catch (IndexOutOfBoundsException e) {
                throw new ParenthesisMismatchedException(e);
            }
        }
    }

    private <T,C> Extractor<T,C> extractFromList(final List<Node<T,C>> nodeList) {
        return new Extractor<>(nodeList);
    }
}
