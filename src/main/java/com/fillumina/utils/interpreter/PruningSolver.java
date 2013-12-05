package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This {@link Solver} runs in the same way as {@link DefaultSolver} but
 * if it encounters an exception of type {@link ContextException} meaning that
 * a variable was not found in the context it simply stop executing that
 * branch and pass over. By this way the tree can be optimized before being
 * executed in a loop where some variables in the context changes (i.e.
 * producing a graph using 'x' and 'y' as variables).
 * <p>
 * <b>NOTE:</b> this class modifies the solution tree.
 * <p>
 * <b>NOTE:</b> always use this class to get the solution (don't use
 * {@link DefaultSolver}).
 *
 * @param T the type of the elements
 * @param C the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PruningSolver implements Solver, Serializable {
    private static final long serialVersionUID = 1L;

    public static final PruningSolver INSTANCE = new PruningSolver();

    /**
     * This solver try to solve the expression and at the same time
     * cuts the tree substituting the values that it founds into the nodes.
     * It's useful to pre-parse and optimize an expression that should be
     * executed several times.
     *
     * @param nodeTree  the solution tree
     * @param context   the context
     * @return  the list of values or null if an undefined variable
     *          has been found.
     */
    @Override
    public <T, C> List<T> solve(final List<Node<T,C>> nodeTree, final C context) {
        final List<T> params = new ArrayList<>(nodeTree.size());
        boolean variableFound = false;
        for (Node<T,C> node : nodeTree) {
            T evaluated = null;
            if (node.hasValue()) {
                evaluated = node.getValue();
            } else {
                @SuppressWarnings("unchecked")
                final List<T> solved = node.hasChildren() ?
                        solve(node.getChildren(), context) :
                        Collections.EMPTY_LIST;
                if (solved != null) {
                    try {
                        evaluated = evaluate(node, solved, context);
                        node.setValue(evaluated);
                    } catch (ContextException ex) {
                        // an undefined variable has been found
                        variableFound = true;
                    }
                } else {
                    variableFound = true;
                }
            }
            params.add( evaluated);
        }
        // returns null if a variable has been found
        return variableFound ? null : params;
    }

    private <T, C> T evaluate(final Node<T,C> node,
            final List<T> params, final C context) {
        final GrammarElement<T,C> grammarElement = node.getGrammarElement();
        try {
            return grammarElement.evaluate(node.getExpression(), params, context);
        } catch (ContextException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EvaluationException(node.getExpression(), ex);
        }
    }

}
