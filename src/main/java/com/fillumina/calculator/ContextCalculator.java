package com.fillumina.calculator;

import java.util.List;
import java.util.Map;

/**
 * A {@link Calculator} that uses a mapped context of the form
 * {@code Map<String,T>}.
 *
 * @see Calculator
 *
 * @param T the type of the result
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ContextCalculator<T>
        extends SimplifyingCalculator<T,Map<String,T>> {
    private static final long serialVersionUID = 1L;

    public ContextCalculator(
            final Iterable<GrammarElement<T, Map<String, T>>> grammar) {
        super(grammar);
    }

    public ContextCalculator(final Interpreter<T, Map<String, T>> interpreter,
            final Solver solver, final Solver simplifier) {
        super(interpreter, solver, simplifier);
    }

    @Override
    public ContextSolutionTree<T> createSolutionTree(final String expression) {
        final List<Node<T,Map<String,T>>> solutionTree =
                createSolutionTreeList(expression);
        return new ContextSolutionTree<>(getSolver(), getSimplifier(),
                solutionTree);
    }
}
