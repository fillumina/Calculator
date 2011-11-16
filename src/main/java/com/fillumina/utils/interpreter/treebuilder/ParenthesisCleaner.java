package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import java.util.List;
import java.util.ListIterator;

/**
 * Removes the parenthesis with only one children (parameter)
 * @author fra
 */
public class ParenthesisCleaner {

    public void clean(final List<Node> nodes) {
        ListIterator<Node> iterator = nodes.listIterator();
        while(iterator.hasNext()) {
            final Node node = iterator.next();
            if (node.isOfType(OpenParenthesis.class)) {
                if (node.hasOnlyOneChild()) {
                    iterator.remove();
                    iterator.add(node.getChildren().get(0));
                    iterator = nodes.listIterator(); // restart the parsing
                } else if (node.isChildrenNumber(0)) {
                    throw new ParenthesisMismatchedException();
                }
            } else {
                clean(node.getChildren());
            }
        }
    }

}
