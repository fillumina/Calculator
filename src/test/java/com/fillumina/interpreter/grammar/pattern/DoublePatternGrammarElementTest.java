package com.fillumina.interpreter.grammar.pattern;

import com.fillumina.interpreter.GrammarElement;
import com.fillumina.interpreter.grammar.DoublePatternElement;
import com.fillumina.interpreter.grammar.fast.DoubleFastElementTest;

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
