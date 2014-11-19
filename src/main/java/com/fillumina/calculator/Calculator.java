package com.fillumina.calculator;

import com.fillumina.calculator.interpreter.DefaultInterpreter;
import java.io.Serializable;
import java.util.List;

/**
 * Solves an expression.
 * It uses two components: an {@link Interpreter} that parses the expression
 * and builds a solution tree and a {@link Solver} that solves the solution tree.
 *
 * @param T the type of the result
 * @param C the type of the context
 *
 * @see SimplityingCalculator
 * @see MappedContextSimplifyingCalculator
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Calculator<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interpreter<T,C> interpreter;
    private final Solver solver;

    /**
     * Uses the default {@link Interpreter} initialized with
     * the given {@param grammar} and the {@link DefaultSolver}.
     *
     * @see com.fillumina.calculator.grammar.Grammar
     * @see com.fillumina.calculator.grammar.instance.ArithmeticGrammar
     * @see com.fillumina.calculator.grammar.instance.BooleanGrammar
     *
     * @param grammar   the grammar that will be used to parse the expression
     */
    public Calculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(new DefaultInterpreter<>(grammar), DefaultSolver.INSTANCE);
    }

    /**
     * Uses the default {@link Interpreter} initialized with
     * the given {@param grammar} and the given {@link Solver}.
     *
     * @see com.fillumina.calculator.grammar.Grammar
     * @see com.fillumina.calculator.grammar.instance.ArithmeticGrammar
     * @see com.fillumina.calculator.grammar.instance.BooleanGrammar
     * @see DefaultSolver
     * @see SimplifyingSolver
     *
     * @param grammar   the grammar that will be passed to the default
     *                  {@link Interpreter} to create the solution tree.
     * @param solver    used to solve the solution tree.
     */
    public Calculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this(new DefaultInterpreter<>(grammar), solver);
    }

    /**
     * The most basic constructor allows to build a {@link Calculator} based
     * on a custom {@link Interpreter} and {@link Solver}.
     *
     * @see DefaultInterpreter
     * @see DefaultSolver
     * @see SimplifyingSolver
     *
     */
    public Calculator(final Interpreter<T, C> interpreter,
            final Solver solver) {
        this.interpreter = interpreter;
        this.solver = solver;
    }

    protected Solver getSolver() {
        return solver;
    }

    /**
     * A {@link SolutionTree} encapsulates a solution that can be queried for
     * unsolved variables (incognita) and solved iteratively.
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
    public SolutionTree<T,C> createSolutionTree(final String expression) {
        final List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return new SolutionTree<>(solver, solutionTree);
    }

    /**
     * @param expression   the expression to be solved
     * @return the first result or {@code null} if no solution can be found.
     * @throws SyntaxErrorException (<b>parent of all related exceptions</b>)
     *                      in case of empty expression
     * @throws ContextException for variables not in defined in context
     * @throws com.fillumina.calculator.treebuilder.ParenthesesMismatchedException
     *                      for parentheses mismatch
     * @throws EvaluationException for errors in the expression
     * @throws com.fillumina.calculator.grammar.GrammarException
     *                      for errors in the {@link GrammarElement}s.
     */
    public T solveSingleValue(final String expression) {
        return solve(null, expression).get(0);
    }

    /**
     * @param context       the context to pass to
     *                      {@link GrammarElement#evaluate(String,List,Object)}.
     * @param expression    the expression to be solved
     * @return the first result or {@code null} if no solution can be found.
     * @throws SyntaxErrorException (<b>parent of all related exceptions</b>)
     *                      in case of empty expression
     * @throws ContextException for variables not in defined in context
     * @throws com.fillumina.calculator.treebuilder.ParenthesesMismatchedException
     *                      for parentheses mismatch
     * @throws EvaluationException for errors in the expression
     * @throws com.fillumina.calculator.grammar.GrammarException
     *                      for errors in the {@link GrammarElement}s.
     */
    public T solveSingleValue(final C context, final String expression) {
        return solve(context, expression).get(0);
    }

    /**
     * @param expression    the string expression to solve.
     * @return      the entire (eventually multi-rooted or partially solved)
     *              solution tree. For most uses it is preferable to use
     *              {@link #createSolutionTree(String, Object)}.
     * @throws SyntaxErrorException (<b>parent of all related exceptions</b>)
     *                      in case of empty expression
     * @throws ContextException for variables not in defined in context
     * @throws com.fillumina.calculator.treebuilder.ParenthesesMismatchedException
     *                      for parentheses mismatch
     * @throws EvaluationException for errors in the expression
     * @throws com.fillumina.calculator.grammar.GrammarException
     *                      for errors in the {@link GrammarElement}s.
     */
    public List<T> solve(final String expression) {
        List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return solver.solve(solutionTree, null);
    }

    /**
     * @param context       the context that will be passed to
     *                      {@link GrammarElement#evaluate(String,List,Object)}.
     * @param expression    the string expression to solve.
     * @return              the entire (eventually multi-rooted or partially
     *                      solved) solution tree as a list.
     * @throws SyntaxErrorException (<b>parent of all related exceptions</b>)
     *                      in case of empty expression
     * @throws ContextException for variables not in defined in context
     * @throws com.fillumina.calculator.treebuilder.ParenthesesMismatchedException
     *                      for parentheses mismatch
     * @throws EvaluationException for errors in the expression
     * @throws com.fillumina.calculator.grammar.GrammarException
     *                      for errors in the {@link GrammarElement}s.
     */
    public List<T> solve(final C context, final String expression) {
        List<Node<T,C>> solutionTree = createSolutionTreeList(expression);
        return solver.solve(solutionTree, context);
    }

    protected List<Node<T, C>> createSolutionTreeList(final String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new SyntaxErrorException("empty expression");
        }
        return interpreter.buildSolutionTree(expression);
    }
}
