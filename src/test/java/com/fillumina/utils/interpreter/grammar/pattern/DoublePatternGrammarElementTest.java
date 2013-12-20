package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.FastDoubleGrammarElementTest;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoublePatternGrammarElementTest
        extends FastDoubleGrammarElementTest {

    @Override
    protected GrammarElement<Double, Void> getNumberGrammarElement() {
        return (GrammarElement<Double, Void>)
                DoublePatternGrammarElement.INSTANCE;
    }
}
