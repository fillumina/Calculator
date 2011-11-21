package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.Operator;
import java.util.List;

/**
 *
 * @author fra
 */
public class ReadOperatorParameters {

    public void read(final List<Node> list,
            final IndexedNode higherPriority) {
        final int index = higherPriority.getIndex();
        final Node node = list.get(index);

        final OperatorParameters operator =
                new OperatorParameters(higherPriority);

        final ExtendedListIterator<Node> iterator =
                new ExtendedListIterator<Node>(list, operator.startOperandsIndex);

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
            final Node childNode = iterator.nextAndRemove();
            if (childNode.isOfType(OpenParenthesis.class) && childNode.hasChildren()) {
                node.addAllChildren(childNode.getChildren());
                break;
            }
            node.addChildren(childNode);
        }
    }

    private static class OperatorParameters {
        private final int startOperandsIndex;
        private final int operandsBefore;
        private final int operandsAfter;

        public OperatorParameters(final IndexedNode indexedNode) {
            final Operator<?,?> operator = (Operator<?,?>)
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
