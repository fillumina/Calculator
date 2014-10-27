package com.fillumina.interpreter.grammar;

import com.fillumina.interpreter.Node;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds all undefined variables in the solution tree.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UndefinedVariablesFinder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final UndefinedVariablesFinder INSTANCE =
            new UndefinedVariablesFinder();

    private UndefinedVariablesFinder() {}

    public <T,C> List<String> find(final List<Node<T,C>> solutionTree) {
        final List<String> undefinedVariables = new ArrayList<>();
        find(solutionTree, undefinedVariables);
        return undefinedVariables;
    }

    private <T,C> void find(final List<Node<T,C>> solutionTree,
            final List<String> undefinedVariables) {
        for (final Node<T,C> node: solutionTree) {
            if (!node.hasValue() &&
                    node.getType() == GrammarElementType.UNRECOGNIZED) {
                undefinedVariables.add(node.getExpression());
            }
            if (node.hasChildren()) {
                find(node.getChildren(), undefinedVariables);
            }
        }
    }
}
