package com.fillumina.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This {@link Solver} modifies the given {@link SolutionTree} by substituting
 * nodes with results while solving the expression. It acts like an
 * expression simplifier. It doesn't throw a {@link ContextException} in case
 * of undefined variables.
 * <p>
 * <b>NOTE:</b> this class <i>modifies</i> the solution tree.
 *
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;

    public static final SimplifyingSolver INSTANCE =
            new SimplifyingSolver(DefaultNodeEvaluator.INSTANCE);

    private final NodeEvaluator evaluator;

    /** Evaluates {@link Node}s with a {@link NodeEvaluator}. */
    public SimplifyingSolver(final NodeEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * This solver tries to solve the expression and at the same time
     * modify the tree substituting the values that it solves into the nodes.
     * It's useful to pre-parse and optimize an expression that should be
     * executed several times. Differently from
     * {@link DefaultSolver#solve(List,Object)} it doesn't throw
     * {@link ContextException} if a required variable is not present in
     * the context.
     *
     * @param nodeTree  the solution tree
     * @param context   the context
     * @return  the list of values or {@code null} if an undefined variable
     *          has been found.
     */
    @Override
    public <T, C> List<T> solve(final List<Node<T,C>> nodeTree,
            final C context) {
        final List<T> results = new ArrayList<>(nodeTree.size());
        boolean variableFound = false;
        for (final Node<T,C> node : nodeTree) {
            T evaluated = null;
            if (node.hasValue()) {
                evaluated = node.getValue();
            } else {
                @SuppressWarnings("unchecked")
                final List<T> params = node.hasChildren() ?
                        solve(node.getChildren(), context) :
                        Collections.EMPTY_LIST;
                if (params != null) {
                    try {
                        evaluated = evaluator.evaluate(node, params, context);
                        node.setValueAndRemoveChildren(evaluated);
                    } catch (ContextException ex) {
                        variableFound = true;
                    }
                } else {
                    variableFound = true;
                }
            }
            if (!variableFound) {
                results.add( evaluated);
            }
        }
        // returns null if a variable has been found
        return variableFound ? null : results;
    }
}
