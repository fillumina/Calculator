package com.fillumina.calculator.grammar;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Evaluator<T,C> {

    T evaluate(String value, C context);
}
