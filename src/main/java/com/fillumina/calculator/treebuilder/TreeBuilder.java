package com.fillumina.calculator.treebuilder;

import com.fillumina.calculator.Node;
import com.fillumina.calculator.interpreter.SolutionTreeFilter;
import java.io.Serializable;
import java.util.List;

/**
 * Takes a flat list of parsed {@link GrammarElement}s and transforms it into a
 * solution tree based on parentheses and operator priorities.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TreeBuilder implements SolutionTreeFilter, Serializable {
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
        IndexedNode<T,C> higherPriorityOp;
        while( exists( higherPriorityOp =
                HigherPriorityOperatorFinder.INSTANCE.find(nodeList))) {
            OperatorParametersReader.INSTANCE.read(nodeList, higherPriorityOp);
        }
    }

    private boolean notNull(final Object obj) {
        return obj != null;
    }

    private boolean exists(final Object node) {
        return node != IndexedNode.NULL;
    }
}
