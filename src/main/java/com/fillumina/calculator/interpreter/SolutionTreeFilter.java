package com.fillumina.calculator.interpreter;

import com.fillumina.calculator.Node;
import java.util.List;

/**
 * General interface for classes that modify a solution tree.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface SolutionTreeFilter {

    <T,C> void executeOn(final List<Node<T,C>> list);
}
