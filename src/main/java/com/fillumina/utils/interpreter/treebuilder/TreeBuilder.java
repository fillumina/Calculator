package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreeBuilder<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final InnerParenthesisFinder innerParenthesisFinder;
    private final HigherPriorityOperatorFinder higherPriorityOperatorFinder;
    private final ReadOperatorParameters loadOperatorParameters;

    public TreeBuilder() {
        innerParenthesisFinder = InnerParenthesisFinder.INSTANCE;
        higherPriorityOperatorFinder = HigherPriorityOperatorFinder.INSTANCE;
        loadOperatorParameters = ReadOperatorParameters.INSTANCE;
    }

    public void createTree(final List<Node<T,C>> nodeList) {
        Node<T,C> innerParenthesis;
        while( notNull( innerParenthesis =
                innerParenthesisFinder.find(nodeList))) {
            readOperatorsByPriority(innerParenthesis.getChildren());
        }

        readOperatorsByPriority(nodeList);
    }

    private void readOperatorsByPriority(final List<Node<T,C>> nodeList) {
        IndexedNode<T,C> higherPriorityOperator;
        while( exists( higherPriorityOperator =
                higherPriorityOperatorFinder.find(nodeList))) {
            loadOperatorParameters.read(nodeList, higherPriorityOperator);
        }
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

    private boolean exists(final IndexedNode<T,C> node) {
        return node != IndexedNode.NULL;
    }
}
