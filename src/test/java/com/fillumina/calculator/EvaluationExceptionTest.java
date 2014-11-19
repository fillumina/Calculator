package com.fillumina.calculator;

import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EvaluationExceptionTest {

    @Test(expected=EvaluationException.class)
    public void shouldCallEvaluationExceptionIfEvaluationGoesWrong() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new ContextedGrammarBuilder<Integer>()
                .addFloatingPointOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            // THIS IS AN ERROR !
                            return Integer.valueOf(value);
                        }
                    })
                .buildGrammar());

        calc.solveSingleValue("12.34");
    }
}
