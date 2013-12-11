package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParametrizedCalculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;

    public ParametrizedCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this.interpreter = new Interpreter<>(grammar);
    }

    @SuppressWarnings("unchecked")
    public OptimizedSolutionTree<T,C> createSolutionTree(
            final String expression) {
        return createSolutionTree(expression, (C) Collections.EMPTY_MAP);
    }

    /**
     * The variables passed in {@code context} will be evaluated only once
     * with the value passed here.
     */
    public OptimizedSolutionTree<T,C> createSolutionTree(
            final String expression, final C context) {
        final List<Node<T,C>> solutionTree = interpreter.parse(expression);
        final List<T> solution =
                PruningSolver.INSTANCE.solve(solutionTree, context);
        return new OptimizedSolutionTree<>(solutionTree, solution);
    }
}
