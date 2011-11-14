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

    private final List<Node> nodeList;

    public InnerParenthesisFinder(final List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    /**
    * Extract a sublist of the inner parenthesis content and put it
    * in the parameters of the open parenthesis (the close one is removed).
    */
    public Node find() {
        IndexedNode openPar = IndexedNode.NULL;

        final ListIterator<Node> iterator = nodeList.listIterator();
        while (iterator.hasNext()) {
            final Node node = iterator.next();

            if (isAnEmptyOpenParenthesis(node)) {
                openPar = new IndexedNode(node, getCurrentIndex(iterator));

            } else if (isCloseParenthesis(node)) {
                iterator.remove();
                if (openPar.getIndex() == getCurrentIndex(iterator)) {
                    iterator.previous();
                    iterator.remove();
                } else {
                    // extract the list of tokens comprises in the inner parenthesis
                    final List<Node> innerParenthesisList=
                            extractInnerNodesSublist(openPar, iterator);
                    // add them to the list of the open parenthesis
                    openPar.getNode().addAllChildren(innerParenthesisList);
                    // remove them from the main list
                    // NOTE: removing from a sublist removes from the list
                    // it belongs from (a sublist is a view of the belonging-from list)
                    innerParenthesisList.clear();
                    return openPar.getNode();
                }
            }
        }
        return null;
    }

    private List<Node> extractInnerNodesSublist(
            final IndexedNode prevParenthesis,
            final ListIterator<Node> iterator) {
        try {
            return nodeList.subList(
                    prevParenthesis.getIndex(), getCurrentIndex(iterator));
        } catch (IndexOutOfBoundsException e) {
            throw new ParenthesisMismatchedException(e);
        }
    }

    private boolean isCloseParenthesis(final Node node) {
        return node.getGrammarElement() instanceof CloseParenthesis;
    }

    /**
     * An already processed open parenthesis contains as its children
     * all the nodes inside the parenthesis. So if a parenthesis has no
     * nodes it means it is not processed yet.
     */
    private boolean isAnEmptyOpenParenthesis(final Node node) {
        return node.getGrammarElement() instanceof OpenParenthesis &&
                node.hasNoChildren();
    }

    private int getCurrentIndex(final ListIterator<Node> iterator) {
        return iterator.previousIndex() + 1;
    }

}
