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
    private final ReadOperatorParameters readOperatorParameters;

    public TreeBuilder() {
        innerParenthesisFinder = new InnerParenthesisFinder();
        higherPriorityOperatorFinder = new HigherPriorityOperatorFinder();
        readOperatorParameters = new ReadOperatorParameters();
    }

    public void createTree(final List<Node> tokenList) {
        Node innerParenthesis;
        while( notNull( innerParenthesis =
                innerParenthesisFinder.find(tokenList))) {
            readOperatorsByPriority(innerParenthesis.getNodes());
        }

        readOperatorsByPriority(tokenList);
    }

    private void readOperatorsByPriority(final List<Node> list) {
        int higherPriorityOperatorIndex;
        while( exists( higherPriorityOperatorIndex =
                higherPriorityOperatorFinder.findIndex(list))) {
            readOperatorParameters.read(list, higherPriorityOperatorIndex);
        }
    }

    private boolean exists(final int index) {
        return index != -1;
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

}
