package com.fillumina.calculator.grammar.treebuilder;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.util.ExtendedListIterator;
import com.fillumina.calculator.grammar.GrammarElementType;
import com.fillumina.calculator.Node;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class OperatorParametersReader {

    static final OperatorParametersReader INSTANCE = new OperatorParametersReader();

    public <T,C> void read(final List<Node<T,C>> list,
            final IndexedNode<T,C> higherPriority) {
        final Node<T,C> node = higherPriority.getNode();

        final OperatorParametersIndexes<T,C> operands =
                new OperatorParametersIndexes<>(higherPriority);

        final ExtendedListIterator<Node<T,C>> iterator =
                new ExtendedListIterator<>(list, operands.startIndex);

        for (int i=0; i<operands.before; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            node.addChildren(iterator.nextAndRemove());
        }

        iterator.next(); // jump over the node

        for (int i=0; i<operands.after; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            final Node<T,C> childNode = iterator.nextAndRemove();
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

    private static class OperatorParametersIndexes<T,C> {
        private final int startIndex;
        private final int before;
        private final int after;

        public OperatorParametersIndexes(final IndexedNode<T,C> indexedNode) {
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
