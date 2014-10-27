package com.fillumina.calculator.grammar.pattern;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.DoublePatternElement;
import com.fillumina.calculator.grammar.fast.DoubleFastElementTest;

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
