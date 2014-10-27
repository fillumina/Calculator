package com.fillumina.interpreter;

import java.io.Serializable;
import java.util.List;

/**
 * Takes a string expression and creates a solving tree using an
 * {@link DefaultInterpreter} and solves it using a {@link Solver}.
 *
 * @param T the type of the elements (i.e. <code>Double</code>)
 * @param C the type of the context (i.e. <code>Map&lt;String, Double&gt;</code>)
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultCalculator<T,C> implements Calculator<T, C>, Serializable {
    private static final long serialVersionUID = 1L;

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
     * defined in a grammar. Only the first one will be considered.
     *
     * @param grammar an iterable giving the {@link GrammarElement}s
     *                in priority order (higher first).
     */
    @SuppressWarnings("unchecked")
    public DefaultCalculator(final Iterable<GrammarElement<T,C>> grammar) {
        this(new DefaultInterpreter<>(grammar), DefaultSolver.INSTANCE);
    }

    public DefaultCalculator(final Iterable<GrammarElement<T,C>> grammar,
            final Solver solver) {
        this(new DefaultInterpreter<>(grammar), solver);
    }

    /** Defines your own interpreter and solver. */
    public DefaultCalculator(final DefaultInterpreter<T,C> interpreter,
            final Solver solver) {
        this.interpreter = interpreter;
        this.solver = solver;
    }

    /** Returns the first result. */
    @Override
    public T solveSingleValue(final String expression, final C context) {
        return solve(expression, context).get(0);
    }

    /** Returns the entire (eventually multi-rooted) solved tree. */
    @Override
    public List<T> solve(final String expression, final C context) {
        final List<Node<T,C>> solutionTree =
                interpreter.buildSolutionTree(expression);
        return solver.solve(solutionTree, context);
    }
}
