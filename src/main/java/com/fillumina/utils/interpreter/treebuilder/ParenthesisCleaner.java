package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.SolutionTreeModifier;
import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * Removes the parenthesis with only one children (parameter)
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParenthesisCleaner implements SolutionTreeModifier, Serializable {
    private static final long serialVersionUID = 1L;

    public static final ParenthesisCleaner INSTANCE =
            new ParenthesisCleaner();

    @Override
    public <T,C> void executeOn(final List<Node<T,C>> nodes) {
        ListIterator<Node<T,C>> iterator = nodes.listIterator();
        while(iterator.hasNext()) {
            final Node<T,C> node = iterator.next();
            if (node.isOfType(GrammarElementType.OPEN_PAR)) {
                if (node.hasOnlyOneChild()) {
                    iterator.remove();
                    iterator.add(node.getChildren().get(0));
                    iterator = nodes.listIterator(); // restart the parsing
                } else if (!node.hasChildren()) {
                    throw new ParenthesisMismatchedException();
                }
            } else {
                executeOn(node.getChildren());
            }
        }
    }
}
