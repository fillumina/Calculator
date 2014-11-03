package com.fillumina.calculator.treebuilder;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.Node;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class OperatorParametersReader {

    static final OperatorParametersReader INSTANCE = new OperatorParametersReader();

    public <T,C> void read(final List<Node<T,C>> list,
            final IndexedNode<T,C> higherPriorityNode) {
        final Node<T,C> node = higherPriorityNode.getNode();

        final OperatorParameters<T,C> operands =
                new OperatorParameters<>(higherPriorityNode);

        final ListIterator<Node<T,C>> iterator =
                list.listIterator(operands.startIndex);

        for (int i=0; i<operands.before; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            node.addChildren(getNextAndRemove(iterator));
        }

        iterator.next(); // jump over the node

        for (int i=0; i<operands.after; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            final Node<T,C> childNode = getNextAndRemove(iterator);
            if (childNode.isOfType(GrammarElementType.OPEN_PAR) &&
                    childNode.hasChildren()) {
                node.addAllChildren(childNode.getChildren());
                if (i==0) {
                    // if a parenthesis follows an operator only the element
                    // in the parenthesis will be added as parameters to the
                    // operator (this will allow to use variable number
                    // operators in an expression).
                    break;
                }
            } else {
                node.addChildren(childNode);
            }
        }
    }

    private static <K> K getNextAndRemove(final Iterator<K> iterator) {
        final K t = iterator.next();
        iterator.remove();
        return t;
    }

    private static class OperatorParameters<T,C> {
        private final int startIndex;
        private final int before;
        private final int after;

        public OperatorParameters(final IndexedNode<T,C> indexedNode) {
            final GrammarElement<T,C> grammarElement =
                    indexedNode.getNode().getGrammarElement();
            after = grammarElement.getRequiredOperandsAfter();

            final int leftOperands = grammarElement.getRequiredOperandsBefore();
            final int firstIndex = indexedNode.getIndex() - leftOperands;

            if (firstIndex < 0 ) {
                startIndex = 0;
                before = indexedNode.getIndex() - 1;
            } else {
                startIndex = firstIndex;
                before = leftOperands;
            }
        }

    }
}
