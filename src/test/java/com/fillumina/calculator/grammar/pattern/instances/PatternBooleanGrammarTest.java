package com.fillumina.calculator.grammar.pattern.instances;

import com.fillumina.calculator.grammar.pattern.instances.BooleanGrammar;
import com.fillumina.calculator.Calculator;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return Calculator.createFast(BooleanGrammar.INSTANCE);
    }
}