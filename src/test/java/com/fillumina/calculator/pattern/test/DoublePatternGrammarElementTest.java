package com.fillumina.calculator.pattern.test;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.element.DoubleOperandTest;
import com.fillumina.calculator.grammar.DoublePatternElement;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternGrammarElementTest
        extends DoubleOperandTest {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return (GrammarElement<Double, Void>)
                DoublePatternElement.INSTANCE;
    }
}