package com.fillumina.calculator.grammar.fast.instances;

import com.fillumina.calculator.grammar.fast.instances.FastBooleanGrammar;
import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.pattern.instances.BooleanGrammarTestBase;
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