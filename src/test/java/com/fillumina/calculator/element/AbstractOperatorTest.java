package com.fillumina.calculator.element;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.SettableContextedGrammarBuilder;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractOperatorTest {

    @Test
    public void shouldFilterOutCommonWhiteSpaces() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
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
                .buildGrammar());

        assertEquals(13, calc.solveSingleValue("@ 12"), 0);
    }

}
