package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.DoublePatternElement;
import com.fillumina.utils.interpreter.grammar.fast.DoubleFastElementTest;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternGrammarElementTest
        extends DoubleFastElementTest {

    @Override
    protected GrammarElement<Double, Void> getNumberGrammarElement() {
        return (GrammarElement<Double, Void>)
                DoublePatternElement.INSTANCE;
    }
}
