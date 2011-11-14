package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class ParenthesisCleaner {

    public void clean(final List<Node> nodes) {
        ListIterator<Node> iterator = nodes.listIterator();
        while(iterator.hasNext()) {
            final Node child = iterator.next();
            if (child.isOfType(OpenParenthesis.class)) {
                if (child.hasOnlyOneChild()) {
                    iterator.remove();
                    iterator.add(child.getChildren().get(0));
                    iterator = nodes.listIterator(); // restart the parsing
                } else if (child.isChildrenNumber(0)) {
                    throw new ParenthesisMismatchedException();
                }
            } else {
                clean(child.getChildren());
            }
        }
    }

}
