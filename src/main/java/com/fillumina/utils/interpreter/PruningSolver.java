package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This {@link Solver} runs in the same way as the {@link DefaultSolver} but
 * if it encounters an undefined variable it simply saves the calculation up to
 * that, prunes the solution tree, stops executing that branch
 * and passes over. This is useful to pre-optimize a solution tree before
 * executing it in a loop (i.e. when graphing).
 * <p>
 * <b>NOTE:</b> this class modifies the solution tree.
 * <p>
 * <b>NOTE:</b> if you start using this class you should keep using this class
 * in your calculation because it makes use of some features of {@link Node}
 * that other {@link Solver}s may not understand.
 *
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PruningSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;

    public static final PruningSolver INSTANCE =
            new PruningSolver(new DefaultEvaluator());

    private final Evaluator evaluator;

    public PruningSolver(final Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * This solver tries to solve the expression and at the same time
     * cuts the tree substituting the values that it founds into the nodes.
     * It's useful to pre-parse and optimize an expression that should be
     * executed several times.
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
            results.add( evaluated);
        }
        // returns null if a variable has been found
        return variableFound ? null : results;
    }
}
