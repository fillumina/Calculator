package com.fillumina.calculator.grammar.instance;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.pattern.test.instances.BooleanGrammarTestBase;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return Calculator.createFast(BooleanGrammar.INSTANCE);
    }
}