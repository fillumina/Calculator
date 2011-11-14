package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fra
 */
public class TreeBuilder implements Serializable {
    private static final long serialVersionUID = 1L;

    private final HigherPriorityOperatorFinder higherPriorityOperatorFinder;
    private final ReadOperatorParameters readOperatorParameters;

    public TreeBuilder() {
        higherPriorityOperatorFinder = new HigherPriorityOperatorFinder();
        readOperatorParameters = new ReadOperatorParameters();
    }

    public void createTree(final List<Node> tokenList) {
        Node innerParenthesis;
        while( notNull( innerParenthesis =
                new InnerParenthesisFinder(tokenList).find())) {
            readOperatorsByPriority(innerParenthesis.getChildren());
        }

        readOperatorsByPriority(tokenList);
    }

    private void readOperatorsByPriority(final List<Node> list) {
        IndexedNode higherPriorityOperator;
        while( exists( higherPriorityOperator =
                higherPriorityOperatorFinder.findIndex(list))) {
            readOperatorParameters.read(list, higherPriorityOperator);
        }
    }

    private boolean exists(final IndexedNode node) {
        return node != IndexedNode.NULL;
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

}
