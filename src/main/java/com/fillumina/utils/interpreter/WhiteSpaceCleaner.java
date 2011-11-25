package com.fillumina.utils.interpreter;

import java.util.List;
import java.util.ListIterator;

/**
 * The {@link WhiteSpace} defines characters in the input that may be
 * safely ignored. This class removes them from the list of tokens.
 *
 * @author fra
 */
public class WhiteSpaceCleaner<T,C> {

    public void clean(final List<Node<T,C>> list) {
        final ListIterator<Node<T,C>> iterator = list.listIterator();
        while(iterator.hasNext()) {
            final Node<T,C> node = iterator.next();

            if (isAWhiteSpace(node)) {
                iterator.remove();
            } else {
                clean(node.getChildren());
            }
        }
    }

    private boolean isAWhiteSpace(final Node<T,C> node) {
        final GrammarElement<T, C> ge = node.getGrammarElement();
        return ge == null ? false : ge.isType(GrammarElement.Type.WHITE_SPACE);
    }

}
