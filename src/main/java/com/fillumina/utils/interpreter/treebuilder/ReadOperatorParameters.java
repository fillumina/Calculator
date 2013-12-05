package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.util.ExtendedListIterator;
import com.fillumina.utils.interpreter.GrammarElementType;
import com.fillumina.utils.interpreter.Node;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
class ReadOperatorParameters {

    static final ReadOperatorParameters INSTANCE = new ReadOperatorParameters();

    public <T,C> void read(final List<Node<T,C>> list,
            final IndexedNode<T,C> higherPriority) {
        final int index = higherPriority.getIndex();
        final Node<T,C> node = list.get(index);

        final OperatorParameters<T,C> operator =
                new OperatorParameters<>(higherPriority);

        final ExtendedListIterator<Node<T,C>> iterator =
                new ExtendedListIterator<>(list, operator.startOperandsIndex);

        for (int i=0; i<operator.operandsBefore; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            node.addChildren(iterator.nextAndRemove());
        }

        iterator.next(); // jump over the node

        for (int i=0; i<operator.operandsAfter; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            final Node<T,C> childNode = iterator.nextAndRemove();
            if (childNode.isOfType(GrammarElementType.OPEN_PAR) && childNode.hasChildren()) {
                node.addAllChildren(childNode.getChildren());
                break;
            }
            node.addChildren(childNode);
        }
    }

    private static class OperatorParameters<T,C> {
        private final int startOperandsIndex;
        private final int operandsBefore;
        private final int operandsAfter;

        public OperatorParameters(final IndexedNode<T,C> indexedNode) {
            final GrammarElement<T,C> operator =
                    indexedNode.getNode().getGrammarElement();
            operandsAfter = operator.getRequiredOperandsAfter();

            final int leftOperands = operator.getRequiredOperandsBefore();
            final int startIndex = indexedNode.getIndex() - leftOperands;

            if (startIndex < 0 ) {
                startOperandsIndex = 0;
                operandsBefore = indexedNode.getIndex() - 1;
            } else {
                startOperandsIndex = startIndex;
                operandsBefore = leftOperands;
            }
        }

    }
}
