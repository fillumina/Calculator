package com.fillumina.utils.interpreter;

import java.util.List;

/**
 * A general interface for classes that modify a solution tree.
 * 
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface SolutionTreeModifier {

    <T,C> void executeOn(final List<Node<T,C>> list);
}
