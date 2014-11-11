package com.fillumina.calculator.instance;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.pattern.test.instances.BooleanGrammarTestBase;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class BooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return new Calculator<>(BooleanGrammar.INSTANCE);
    }
}