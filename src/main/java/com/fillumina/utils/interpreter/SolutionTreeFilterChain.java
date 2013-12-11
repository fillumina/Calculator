package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A filter chain that executes the passed list of filter in the given order.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SolutionTreeFilterChain
        implements SolutionTreeFilter, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<SolutionTreeFilter> filters;

    public SolutionTreeFilterChain(final List<SolutionTreeFilter> filters) {
        this.filters = new ArrayList<>(filters);
    }

    public SolutionTreeFilterChain(final SolutionTreeFilter... filters) {
        this.filters= Arrays.asList(filters);
    }

    @Override
    public <T, C> void executeOn(final List<Node<T, C>> list) {
        for (final SolutionTreeFilter filter: filters) {
            filter.executeOn(list);
        }
    }
}