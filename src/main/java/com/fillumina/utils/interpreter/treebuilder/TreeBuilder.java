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

    private final InnerParenthesisFinder innerParenthesisFinder;
    private final HigherPriorityOperatorFinder higherPriorityOperatorFinder;
    private final ReadOperatorParameters loadOperatorParameters;

    public TreeBuilder() {
        innerParenthesisFinder = new InnerParenthesisFinder();
        higherPriorityOperatorFinder = new HigherPriorityOperatorFinder();
        loadOperatorParameters = new ReadOperatorParameters();
    }

    public void createTree(final List<Node> nodeList) {
        Node innerParenthesis;
        while( notNull( innerParenthesis =
                innerParenthesisFinder.find(nodeList))) {
            readOperatorsByPriority(innerParenthesis.getChildren());
        }

        readOperatorsByPriority(nodeList);
    }

    private void readOperatorsByPriority(final List<Node> nodeList) {
        IndexedNode higherPriorityOperator;
        while( exists( higherPriorityOperator =
                higherPriorityOperatorFinder.find(nodeList))) {
            loadOperatorParameters.read(nodeList, higherPriorityOperator);
        }
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

    private boolean exists(final IndexedNode node) {
        return node != IndexedNode.NULL;
    }
}
