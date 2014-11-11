package com.fillumina.calculator;

import com.fillumina.calculator.interpreter.DefaultInterpreter;
import java.util.List;

/**
 * This {@link Calculator} adds the possibility to specify a simplifying
 * {@link Solver} which is a {@link Solver} able to modify the solution
 * tree substituting a result to inner parentheses when possible.
 * It is useful to minimize the
 * amount of operations to do in case of iterations on the same expression
 * with an incognita (i.e. plotting).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingCalculator<T,C> extends Calculator<T,C> {
    private static final long serialVersionUID = 1L;

    private final Solver simplifier;

    public SimplifyingCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(new DefaultInterpreter<>(grammar),
                DefaultSolver.INSTANCE,
                SimplifyingSolver.INSTANCE);
    }

    /**
     *
     * @param interpreter
     * @param solver        A {@link Solver} able to resolve a solution tree
     *                      without modifying it.
     * @param simplifier    A {@link Solver} that modifies the solution tree
     *                      substituting each branch with its value if possible.
     */
    public SimplifyingCalculator(final Interpreter<T, C> interpreter,
            final Solver solver,
            final Solver simplifier) {
        super(interpreter, solver);
        this.simplifier = simplifier;
    }

    protected Solver getSimplifier() {
        return simplifier;
    }

    /**
     * @return a {@link SolutionTree} that can be used to get the actual solution.
     */
    public SimplifyingSolutionTree<T,C> createSimplifyingSolutionTree(
            final String expression) {
        final List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return new SimplifyingSolutionTree<>(getSolver(), simplifier, solutionTree);
    }
}
