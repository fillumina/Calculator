package com.fillumina.calculator;

import com.fillumina.calculator.util.Mapper;
import java.util.List;
import java.util.Map;

/**
 * A {@link SimplifyingSolutionTree} that uses a map of type
 * {@code Map<String,T>} as context.
 *
 * @param T the type of the result
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class MappedContextSimplifyingSolutionTree<T>
        extends SimplifyingSolutionTree<T, Map<String, T>> {

    public MappedContextSimplifyingSolutionTree(final Solver solver,
            final Solver simplifier,
            final List<Node<T, Map<String, T>>> solutionTree) {
        super(solver, simplifier, solutionTree);
    }

    public MappedContextSimplifyingSolutionTree(
            final SimplifyingSolutionTree<T, Map<String, T>> original) {
        super(original);
    }

    /**
     * Solves the solution tree using the specified variables. This works
     * only if the context is of type {@code Map<String,T>}.<br>
     * Usage:
     * <code><pre>
     * List&lt;Double> l = solutionTree.solveWithVariables("x", 10);
     * </pre></code>
     *
     * @param <T>     the type of the values in the map
     * @param objects the objects (must conform to types in pairs)
     * @return        the solution if found otherwise {@code null}.
     * @throws SyntaxErrorException
     */
    @SuppressWarnings(value = "unchecked")
    public List<T> solveWithVariables(final Object... objects) {
        return solve(Mapper.<T>create(objects));
    }

    /**
     * Solves the solution tree using the specified variables. This works
     * only if the context is of type {@code Map<String,T>}.<br>
     * Usage:
     * <code><pre>
     * List&lt;Double> l = solutionTree.solveWithVariables("x", 10);
     * </pre></code>
     *
     * @param <T>     the type of the values in the map
     * @param objects the objects (must conform to types in pairs)
     * @return        the solution if found otherwise {@code null}.
     * @throws SyntaxErrorException
     */
    @SuppressWarnings(value = "unchecked")
    public List<T> simplifyWithVariables(final Object... objects) {
        return simplify(Mapper.<T>create(objects));
    }

    @Override
    protected MappedContextSimplifyingSolutionTree<T> clone() {
        return new MappedContextSimplifyingSolutionTree<>(this);
    }
}
