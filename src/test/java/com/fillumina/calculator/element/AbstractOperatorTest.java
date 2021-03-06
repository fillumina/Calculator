package com.fillumina.calculator.element;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractOperatorTest {

    @Test
    public void shouldFilterOutCommonWhiteSpaces() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addIntegerOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .add(new AbstractOperator<Integer,Map<String,Integer>>(0, 0, 1, "@") {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public Integer evaluate(String value, List<Integer> params,
                                Map<String, Integer> context) {
                            return params.get(0) + 1;
                        }
                    })
                .buildDefaultGrammar());

        assertEquals(13, calc.solveSingleValue("@ 12"), 0);
    }

}
