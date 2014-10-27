package com.fillumina.interpreter.grammar.fast.instances;

import com.fillumina.interpreter.Calculator;
import com.fillumina.interpreter.grammar.pattern.instances.BooleanGrammarTestBase;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return Calculator.createFast(FastBooleanGrammar.INSTANCE);
    }
}