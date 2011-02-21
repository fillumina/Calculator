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
    * extract a sublist of the inner parenthesis content and put it
    * in the parameters of the open parenthesis (the close one is removed).
    */
    public Node find(final List<Node> list) {
        IndexedNode prevParenthesis = IndexedNode.NULL;

        final ListIterator<Node> iterator = list.listIterator();
        while (iterator.hasNext()) {
            final Node node = iterator.next();

            if (isEmptyOpenParenthesis(node)) {
                prevParenthesis = new IndexedNode(node, getCurrentIndex(iterator));
            } else if (isCloseParenthesis(node)) {
                iterator.remove();
                // extract the list of tokens comprises in the inner parenthesis
                final List<Node> innerParenthesisList = list.subList(
                        prevParenthesis.getIndex(), getCurrentIndex(iterator));
                // add them all to the list of the open parenthesis
                prevParenthesis.getNode().addAll(innerParenthesisList);
                // remove them from the main list
                // NOTE: removing from a sublist removes from the list
                // it belongs from (a sublist is a view of the belonging-from list)
                innerParenthesisList.clear();
                return prevParenthesis.getNode();
            }
        }
        return null;
    }

    private boolean isCloseParenthesis(final Node node) {
        return node.getGrammarElement() instanceof CloseParenthesis;
    }

    private boolean isEmptyOpenParenthesis(final Node node) {
        return node.getGrammarElement() instanceof OpenParenthesis &&
                node.hasNoParameters();
    }

    private int getCurrentIndex(final ListIterator<Node> iterator) {
        return iterator.previousIndex() + 1;
    }

}
