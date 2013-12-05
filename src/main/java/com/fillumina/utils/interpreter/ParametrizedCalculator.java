package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParametrizedCalculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class OptimizedSolutionTree<T,C> {
        private final List<Node<T,C>> solutionTree;
        private final List<T> solution;

        public OptimizedSolutionTree(final List<Node<T, C>> solutionTree,
                final List<T> solution) {
            this.solutionTree = solutionTree;
            this.solution = solution;
        }

        public boolean hasStaticSolution() {
            return solution != null;
        }

        public T getSingleValueSolution() {
            return getSolution().get(0);
        }

        public List<T> getSolution() {
            return solution;
        }

        public T solveSingleValue(final C context) {
            return solve(context).get(0);
        }

        public List<T> solve(final C context) {
            final List<T> solved =
                    PruningSolver.INSTANCE.solve(solutionTree, context);
            return solved;
        }
    }

    private final Interpreter<T,C> interpreter;

    public ParametrizedCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this.interpreter = new Interpreter<>(grammar);
    }

    @SuppressWarnings("unchecked")
    public  OptimizedSolutionTree<T,C> createSolutionTree(
            final String expression) {
        return createSolutionTree(expression, (C) Collections.EMPTY_MAP);
    }

    /**
     * The variables passed in {@code context} will be evaluated only once
     * with the value passed here.
     */
    public  OptimizedSolutionTree<T,C> createSolutionTree(
            final String expression, final C context) {
        final List<Node<T,C>> solutionTree = interpreter.parse(expression);
        final List<T> solution =
                PruningSolver.INSTANCE.solve(solutionTree, context);
        return new OptimizedSolutionTree<>(solutionTree, solution);
    }
}
