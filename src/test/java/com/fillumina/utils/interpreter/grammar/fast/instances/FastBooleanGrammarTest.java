package com.fillumina.utils.interpreter.grammar.fast.instances;

import com.fillumina.utils.interpreter.grammar.pattern.instances.*;
import com.fillumina.utils.interpreter.Calculator;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public Calculator<Boolean,Map<String,Boolean>> getCalculator() {
        return new Calculator<>(FastBooleanGrammar.INSTANCE);
    }
}