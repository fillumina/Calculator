package com.fillumina.interpreter.util;

import com.fillumina.interpreter.Node;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface NodePrinter extends Serializable {

    <T, C> void printNode(StringBuilder builder, int level, Node<T, C> node);

}
