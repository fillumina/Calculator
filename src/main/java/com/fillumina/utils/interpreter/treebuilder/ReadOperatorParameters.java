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
            final int higherPriorityIndex) {
        int index = higherPriorityIndex;
        final Node node = list.get(index);
        final Operator<?,?> operator = (Operator<?,?>) node.getGrammarElement();

        for (int i=0, max=operator.getRequiredOperandsBefore(); i<max; i++) {
            if (index -1 < 0) {
                break; // no more operands
            }
            node.add(list.get(index - 1));
            list.remove(index - 1);
            index--;
        }

        for (int i=0, max=operator.getRequiredOperandsAfter(); i<max; i++) {
            if (index + 1 >= list.size()) {
                break; // no more operands
            }
            final Node childNode = list.get(index + 1);
            if (childNode.isOfType(OpenParenthesis.class) && childNode.hasChildren()) {
                node.addAll(childNode.getNodes());
                list.remove(index + 1);
                break;
            }
            node.add(childNode);
            list.remove(index + 1);
        }
    }

}
