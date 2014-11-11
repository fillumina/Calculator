package com.fillumina.calculator.pattern.test.instances;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.pattern.instances.BooleanPatternGrammar;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return new Calculator<>(BooleanPatternGrammar.INSTANCE);
    }
}