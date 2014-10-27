package com.fillumina.interpreter.grammar.pattern.instances;

import com.fillumina.interpreter.grammar.pattern.instances.BooleanGrammar;
import com.fillumina.interpreter.DefaultCalculator;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBooleanGrammarTest extends BooleanGrammarTestBase {

    @Override
    public DefaultCalculator<Boolean,Map<String,Boolean>> getCalculator() {
        return new DefaultCalculator<>(BooleanGrammar.INSTANCE);
    }
}