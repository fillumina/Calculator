package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * Solves an expression according to the given grammar.
 *
 * @param T the type of the elements (i.e. <code>Double</code>)
 * @param C the type of the context (i.e. <code>Map&lt;String, Double&gt;</code>)
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Calculator<T,C> {
    private final Interpreter<T,C> interpreter;
    private final Solver solver;

    /**
     * A grammar must be passed as an {@link Iterable}. The order
     * of the {@link GrammarElement}s reflects their priority with the
     * higher priority elements first. If you have a function which
     * name is {@code cons} and a variable which name is {@code constant}
     * the variable element should come first in the list.<p>
     *
     * <b>IMPORTANT:</b> There should at most <b>one</b>
     * {@link GrammarElement} of type {@link GrammarElementType#UNRECOGNIZED}
     * defined in a grammar. At any rate only
     * the first one will be considered.
     *
     * @param grammar an iterable giving the {@link GrammarElement}s
     *                in priority order (higher first).
     */
    @SuppressWarnings("unchecked")
    public Calculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(grammar, DefaultSolver.INSTANCE);
    }

    public Calculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this.interpreter = new Interpreter<>(grammar);
        this.solver = solver;
    }

    /** Returns the first result. */
    public T solveSingleValue(final String expression, final C context) {
        return solve(expression, context).get(0);
    }

    /** Returns the entire (eventually multi-rooted) tree. */
    public List<T> solve(final String expression, final C context) {
        final List<Node<T,C>> solutionTree = interpreter.parse(expression);
        return solver.solve(solutionTree, context);
    }
}
