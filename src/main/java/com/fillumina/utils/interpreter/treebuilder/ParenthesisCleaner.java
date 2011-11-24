package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.GrammarElement.Type;
import com.fillumina.utils.interpreter.Node;
import java.util.List;
import java.util.ListIterator;

/**
 * Removes the parenthesis with only one children (parameter)
 * @author fra
 */
public class ParenthesisCleaner<T,C> {

    public void clean(final List<Node<T,C>> nodes) {
        ListIterator<Node<T,C>> iterator = nodes.listIterator();
        while(iterator.hasNext()) {
            final Node<T,C> node = iterator.next();
            if (node.isOfType(Type.OPEN_PAR)) {
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
