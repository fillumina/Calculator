package com.fillumina.interpreter.grammar.fast.instances;

import com.fillumina.interpreter.DefaultCalculator;
import com.fillumina.interpreter.grammar.pattern.instances.BooleanGrammarTestBase;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public DefaultCalculator<Boolean,Map<String,Boolean>> getCalculator() {
        return new DefaultCalculator<>(FastBooleanGrammar.INSTANCE);
    }
}