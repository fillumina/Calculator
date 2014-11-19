package com.fillumina.calculator.grammar;

/**
 * Use to evaluate a string value in builders.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface Evaluator<T,C> {

    T evaluate(String value, C context);
}
