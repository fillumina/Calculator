package com.fillumina.calculator;

import com.fillumina.calculator.interpreter.DefaultInterpreter;
import java.util.List;

/**
 * This {@link Calculator} adds the possibility to specify a simplifying
 * {@link Solver} which is able to modify the solution
 * tree substituting a result value where possible.
 * This is useful to minimize the mount of operations to do in case of
 * iterations on the same expression with an incognita (i.e. plotting).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SimplifyingCalculator<T,C> extends Calculator<T,C> {
    private static final long serialVersionUID = 1L;

    private final Solver simplifier;

    /**
     * Uses the default {@link Interpreter} initialized with
     * the given {@param grammar}, a {@link DefaultSolver} and a
     * {@link SimplifyingSolver}.
     *
     * @see com.fillumina.calculator.grammar.instance.ArithmeticGrammar
     * @see com.fillumina.calculator.grammar.instance.BooleanGrammar
     *
     * @param grammar   the grammar that will be used to parse the expression
     */
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
     * A {@link SimplifyingSolutionTree} encapsulates a solution that can be
     * queried for unsolved variables (incognita) and solved iteratively.
     *
     * @return a {@link SolutionTree} that can be used to get the actual
     *         solution.
     * @throws SyntaxErrorException (<b>parent of all related exceptions</b>)
     *                      in case of empty expression
     * @throws com.fillumina.calculator.treebuilder.ParenthesesMismatchedException
     *                      for parentheses mismatch
     * @throws EvaluationException for errors in the expression
     * @throws com.fillumina.calculator.grammar.GrammarException
     *                      for errors in the {@link GrammarElement}s.
     */
    @Override
    public SimplifyingSolutionTree<T,C> createSolutionTree(
            final String expression) {
        final List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return new SimplifyingSolutionTree<>(getSolver(), simplifier, solutionTree);
    }
}
