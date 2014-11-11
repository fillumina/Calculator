package com.fillumina.calculator.grammar;

import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public interface ParametricEvaluator<T,C> {
    
    T evaluate(String value, List<T> params, C context);
}
