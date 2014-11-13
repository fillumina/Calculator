package com.fillumina.calculator.grammar;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.instance.BooleanGrammar;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SettableContextedGrammarBuilderTest {

    @Test
    public void shouldCreateAGrammarStrartingFromAGivenOne() {
        final Grammar<Boolean,Map<String,Boolean>> grammar = new Grammar<>(
            new SettableContextedGrammarBuilder<>(BooleanGrammar.INSTANCE)
                .addOperator()
                    .symbols("x")
                    .priority(1)
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator
                                <Boolean,Map<String,Boolean>>() {
                        @Override
                        public Boolean evaluate(String value,
                                List<Boolean> params,
                                Map<String, Boolean> context) {
                            boolean p = params.get(0);
                            boolean q = params.get(1);
                            return (p || q) && !(p && q); // XOR
                        }
                    })
                .buildOperator()
            .buildDefaultGrammar());

        final Calculator<Boolean, Map<String,Boolean>> calc =
                new Calculator<>(grammar);

        assertEquals(false, calc.solveSingleValue("true x true"));
    }
}
