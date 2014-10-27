package com.fillumina.interpreter.grammar.pattern.instances;

import com.fillumina.interpreter.Calculator;
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