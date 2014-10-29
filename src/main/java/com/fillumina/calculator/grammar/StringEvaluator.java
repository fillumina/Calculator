package com.fillumina.calculator.grammar;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface StringEvaluator<T,C> {

    T evaluate(String value, C context);
}
