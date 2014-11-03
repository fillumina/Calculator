package com.fillumina.calculator.grammar.pattern.test;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.DoublePatternElement;
import com.fillumina.calculator.grammar.element.DoubleFastElementTest;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternGrammarElementTest
        extends DoubleFastElementTest {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return (GrammarElement<Double, Void>)
                DoublePatternElement.INSTANCE;
    }
}
