package com.fillumina.calculator.grammar.pattern.test.instances;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.pattern.instances.BooleanPatternGrammar;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return Calculator.createFast(BooleanPatternGrammar.INSTANCE);
    }
}