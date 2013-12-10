package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.SolutionTreeModifier;
import java.io.Serializable;
import java.util.List;

/**
 * Takes a flat list of parsed {@link GrammarElement}s and transforms it into a
 * solution tree based on parentheses and operator priorities.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreeBuilder implements SolutionTreeModifier, Serializable {
    private static final long serialVersionUID = 1L;

    public static final TreeBuilder INSTANCE = new TreeBuilder();

    private TreeBuilder() {}

    @Override
    public <T,C> void executeOn(final List<Node<T,C>> nodeList) {
        Node<T,C> innerParenthesis;
        while( notNull( innerParenthesis =
                InnerParenthesisFinder.INSTANCE.find(nodeList))) {
            readOperatorsByPriority(innerParenthesis.getChildren());
        }

        readOperatorsByPriority(nodeList);
    }

    private <T,C> void readOperatorsByPriority(final List<Node<T,C>> nodeList) {
        IndexedNode<T,C> higherPriorityOperator;
        while( exists( higherPriorityOperator =
                HigherPriorityOperatorFinder.INSTANCE.find(nodeList))) {
            ReadOperatorParameters.INSTANCE.read(nodeList, higherPriorityOperator);
        }
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

    private boolean exists(final Object node) {
        return node != IndexedNode.NULL;
    }
}
