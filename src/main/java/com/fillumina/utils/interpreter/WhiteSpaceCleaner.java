package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author fra
 */
public class WhiteSpaceCleaner {

    public void clean(final List<Node> list) {
        final ListIterator<Node> iterator = list.listIterator();
        while(iterator.hasNext()) {
            final Node node = iterator.next();
            
            if (isAWhiteSpace(node)) {
                iterator.remove();
            } else {
                clean(node.getNodes());
            }
        }
    }

    private boolean isAWhiteSpace(final Node node) {
        return node.getGrammarElement() instanceof WhiteSpace;
    }

}
