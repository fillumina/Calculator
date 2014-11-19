package com.fillumina.calculator;

import java.util.List;
import java.util.Map;

/**
 * A {@link SimplifyingCalculator} that uses a mapped context of type
 * {@code Map<String,T>}.
 * It uses two components: an {@link Interpreter} that parses the expression
 * and build a solution tree and a {@link Solver} that solves the solution tree.
 *
 * @param T the type of the result
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class MappedContextSimplifyingCalculator<T>
        extends SimplifyingCalculator<T,Map<String,T>> {
    private static final long serialVersionUID = 1L;

    /**
     * Uses the default {@link Interpreter} initialized with
     * the given {@param grammar} and the {@link DefaultSolver}.
     *
     * @see com.fillumina.calculator.grammar.instance.ArithmeticGrammar
     * @see com.fillumina.calculator.grammar.instance.BooleanGrammar
     *
     * @param grammar   the grammar that will be used to parse the expression
     */
    public MappedContextSimplifyingCalculator(
            final Iterable<GrammarElement<T, Map<String, T>>> grammar) {
        super(grammar);
    }

    /**
     * Uses the default {@link Interpreter} initialized with
     * the given {@param grammar} and the given {@link Solver}.
     *
     * @see com.fillumina.calculator.grammar.instance.ArithmeticGrammar
     * @see com.fillumina.calculator.grammar.instance.BooleanGrammar
     * @see DefaultSolver
     * @see SimplifyingSolver
     *
     * @param grammar   the grammar that will be passed to the default
     *                  {@link Interpreter} to create the solution tree.
     * @param solver    used to solve the solution tree.
     */
    public MappedContextSimplifyingCalculator(
            final Interpreter<T, Map<String, T>> interpreter,
            final Solver solver, final Solver simplifier) {
        super(interpreter, solver, simplifier);
    }

    /**
     * A {@link MappedContextSimplifyingSolutionTree} encapsulates a solution
     * that can be queried for unsolved variables (incognita) and solved
     * iteratively.
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
    public MappedContextSimplifyingSolutionTree<T> createSolutionTree(
            final String expression) {
        final List<Node<T,Map<String,T>>> solutionTree =
                createSolutionTreeList(expression);
        return new MappedContextSimplifyingSolutionTree<>(
                getSolver(),
                getSimplifier(),
                solutionTree);
    }
}
